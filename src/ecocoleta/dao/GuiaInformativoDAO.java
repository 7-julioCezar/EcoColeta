package ecocoleta.dao;

import ecocoleta.model.GuiaInformativo;
import ecocoleta.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GuiaInformativoDAO {

  
    public boolean cadastrar(GuiaInformativo guia) {
        String sql = "INSERT INTO guias_informativos (titulo, conteudo, id_autor) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, guia.getTitulo());
            ps.setString(2, guia.getConteudo());
            ps.setInt(3, guia.getIdAutor());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) guia.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar guia: " + e.getMessage());
        }
        return false;
    }

 
    public List<GuiaInformativo> listarTodos() {
        List<GuiaInformativo> lista = new ArrayList<>();
        String sql = "SELECT * FROM guias_informativos ORDER BY criado_em DESC";

        try (Connection conn = ConexaoDB.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                GuiaInformativo g = new GuiaInformativo();
                g.setId(rs.getInt("id"));
                g.setTitulo(rs.getString("titulo"));
                g.setConteudo(rs.getString("conteudo"));
                g.setIdAutor(rs.getInt("id_autor"));
                lista.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar guias: " + e.getMessage());
        }
        return lista;
    }


    public GuiaInformativo buscarPorId(int id) {
        String sql = "SELECT * FROM guias_informativos WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                GuiaInformativo g = new GuiaInformativo();
                g.setId(rs.getInt("id"));
                g.setTitulo(rs.getString("titulo"));
                g.setConteudo(rs.getString("conteudo"));
                g.setIdAutor(rs.getInt("id_autor"));
                return g;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar guia: " + e.getMessage());
        }
        return null;
    }


    public boolean excluir(int id) {
        String sql = "DELETE FROM guias_informativos WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir guia: " + e.getMessage());
        }
        return false;
    }
}
