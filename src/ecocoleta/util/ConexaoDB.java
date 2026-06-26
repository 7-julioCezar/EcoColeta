package ecocoleta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitário de conexão com o banco de dados MySQL.
 *
 * CONFIGURAÇÃO: Altere as constantes abaixo conforme seu ambiente.
 */
public class ConexaoDB {

    // -------------------------------------------------------
    //  Configurações — edite aqui
    // -------------------------------------------------------
    private static final String URL      = "jdbc:mysql://localhost:3306/ecocoleta?useSSL=false&serverTimezone=America/Sao_Paulo";
    private static final String USUARIO  = "root";
    private static final String SENHA    = "JCMmaça2025"; // coloque sua senha do MySQL

    // -------------------------------------------------------
    //  Método público
    // -------------------------------------------------------

    /**
     * Abre e retorna uma conexão com o banco de dados.
     * Lembre-se de fechar a conexão após o uso (try-with-resources ou .close()).
     */
    public static Connection getConexao() throws SQLException {
        try {
            // Carrega o driver JDBC do MySQL (necessário para versões antigas do Java/NetBeans)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado. Adicione o JAR mysql-connector-j ao projeto.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     * Testa a conexão e imprime o resultado no console.
     * Útil para verificar se as configurações estão corretas.
     */
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
