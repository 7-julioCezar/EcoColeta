package ecocoleta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

 private static final String URL =
        "jdbc:mysql://localhost:3306/ecocoleta?useSSL=false&serverTimezone=America/Sao_Paulo";

private static final String USER = "root";
private static final String PASSWORD = "JCMmaça2025"; 

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado.", e);
        }                                            
        return DriverManager.getConnection(URL, USER, PASSWORD);  
    }                                                  

    public static void testarConexao() {
        System.out.println("=== Testando conexão com o banco de dados ===");
        try (Connection conn = getConexao()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão estabelecida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Falha na conexão: " + e.getMessage());
        }
    }
}