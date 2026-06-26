package ecocoleta.dao;

import ecocoleta.model.Avaliacao;
import ecocoleta.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para operações de Avaliação de pontos de coleta (RF-07).
 */
public class AvaliacaoDAO {

    // ------------------------------------------------------------------
    //  CREATE — Registrar avaliação (RF-07)
    // ------------------------------------------------------------------
    public boolean cadastrar(Avaliacao avaliacao) {
        String sql = "INSERT INTO avaliacoes (nota, comentario, id_usuario, id_ponto) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, avaliacao.getNota());
            ps.setString(2, avaliacao.getComentario());
            ps.setInt(3, avaliacao.getIdUsuario());
            ps.setInt(4, avaliacao.getIdPonto());

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) avaliacao.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar avaliação: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  READ — Listar avaliações de um ponto
    // ------------------------------------------------------------------
    public List<Avaliacao> listarPorPonto(int idPonto) {
        List<Avaliacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM avaliacoes WHERE id_ponto = ? ORDER BY criado_em DESC";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPonto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Avaliacao a = new Avaliacao();
                a.setId(rs.getInt("id"));
                a.setNota(rs.getInt("nota"));
                a.setComentario(rs.getString("comentario"));
                a.setIdUsuario(rs.getInt("id_usuario"));
                a.setIdPonto(rs.getInt("id_ponto"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar avaliações: " + e.getMessage());
        }
        return lista;
    }

    // ------------------------------------------------------------------
    //  READ — Média de notas de um ponto
    // ------------------------------------------------------------------
    public double mediaPorPonto(int idPonto) {
        String sql = "SELECT AVG(nota) AS media FROM avaliacoes WHERE id_ponto = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPonto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("media");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular média: " + e.getMessage());
        }
        return 0.0;
    }
}
