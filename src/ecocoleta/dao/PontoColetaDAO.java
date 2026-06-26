package ecocoleta.dao;

import ecocoleta.model.PontoColeta;
import ecocoleta.model.TipoMaterial;
import ecocoleta.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações de banco de dados da entidade PontoColeta.
 *
 * RF-03: Visualização de pontos de coleta
 * RF-04: Cadastro de novos pontos
 * RF-05: Filtro por tipo de material
 */
public class PontoColetaDAO {

    // ------------------------------------------------------------------
    //  CREATE — Cadastrar ponto de coleta (RF-04)
    // ------------------------------------------------------------------
    public boolean cadastrar(PontoColeta ponto) {
        String sql = "INSERT INTO pontos_coleta (nome, endereco, latitude, longitude, descricao, id_responsavel) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ponto.getNome());
            ps.setString(2, ponto.getEndereco());
            ps.setDouble(3, ponto.getLatitude());
            ps.setDouble(4, ponto.getLongitude());
            ps.setString(5, ponto.getDescricao());
            ps.setInt(6, ponto.getIdResponsavel());

            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    ponto.setId(rs.getInt(1));
                }
                // Salvar materiais vinculados
                vincularMateriais(conn, ponto);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar ponto: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  READ — Listar todos os pontos ativos (RF-03)
    // ------------------------------------------------------------------
    public List<PontoColeta> listarTodos() {
        List<PontoColeta> lista = new ArrayList<>();
        String sql = "SELECT * FROM pontos_coleta WHERE ativo = TRUE ORDER BY nome";

        try (Connection conn = ConexaoDB.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                PontoColeta p = mapearPonto(rs);
                p.setMateriais(buscarMateriasDoPonto(conn, p.getId()));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pontos: " + e.getMessage());
        }
        return lista;
    }

    // ------------------------------------------------------------------
    //  READ — Buscar por ID
    // ------------------------------------------------------------------
    public PontoColeta buscarPorId(int id) {
        String sql = "SELECT * FROM pontos_coleta WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PontoColeta p = mapearPonto(rs);
                p.setMateriais(buscarMateriasDoPonto(conn, p.getId()));
                return p;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ponto: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------------------------------------------
    //  READ — Filtrar por tipo de material (RF-05)
    // ------------------------------------------------------------------
    public List<PontoColeta> filtrarPorMaterial(int idMaterial) {
        List<PontoColeta> lista = new ArrayList<>();
        String sql = "SELECT pc.* FROM pontos_coleta pc "
                   + "INNER JOIN ponto_material pm ON pc.id = pm.id_ponto "
                   + "WHERE pm.id_material = ? AND pc.ativo = TRUE "
                   + "ORDER BY pc.nome";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idMaterial);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PontoColeta p = mapearPonto(rs);
                p.setMateriais(buscarMateriasDoPonto(conn, p.getId()));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao filtrar pontos: " + e.getMessage());
        }
        return lista;
    }

    // ------------------------------------------------------------------
    //  UPDATE — Atualizar ponto
    // ------------------------------------------------------------------
    public boolean atualizar(PontoColeta ponto) {
        String sql = "UPDATE pontos_coleta SET nome=?, endereco=?, latitude=?, longitude=?, descricao=? WHERE id=?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ponto.getNome());
            ps.setString(2, ponto.getEndereco());
            ps.setDouble(3, ponto.getLatitude());
            ps.setDouble(4, ponto.getLongitude());
            ps.setString(5, ponto.getDescricao());
            ps.setInt(6, ponto.getId());

            boolean ok = ps.executeUpdate() > 0;

            if (ok) {
                // Remove vínculos antigos e recria
                removerMateriais(conn, ponto.getId());
                vincularMateriais(conn, ponto);
            }
            return ok;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ponto: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  DELETE — Desativar ponto (soft delete)
    // ------------------------------------------------------------------
    public boolean desativar(int id) {
        String sql = "UPDATE pontos_coleta SET ativo = FALSE WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao desativar ponto: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  Métodos auxiliares privados
    // ------------------------------------------------------------------

    private PontoColeta mapearPonto(ResultSet rs) throws SQLException {
        PontoColeta p = new PontoColeta();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setEndereco(rs.getString("endereco"));
        p.setLatitude(rs.getDouble("latitude"));
        p.setLongitude(rs.getDouble("longitude"));
        p.setDescricao(rs.getString("descricao"));
        p.setAtivo(rs.getBoolean("ativo"));
        p.setIdResponsavel(rs.getInt("id_responsavel"));
        return p;
    }

    private List<TipoMaterial> buscarMateriasDoPonto(Connection conn, int idPonto) throws SQLException {
        List<TipoMaterial> lista = new ArrayList<>();
        String sql = "SELECT tm.* FROM tipos_material tm "
                   + "INNER JOIN ponto_material pm ON tm.id = pm.id_material "
                   + "WHERE pm.id_ponto = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPonto);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TipoMaterial tm = new TipoMaterial();
                tm.setId(rs.getInt("id"));
                tm.setNome(rs.getString("nome"));
                lista.add(tm);
            }
        }
        return lista;
    }

    private void vincularMateriais(Connection conn, PontoColeta ponto) throws SQLException {
        if (ponto.getMateriais() == null || ponto.getMateriais().isEmpty()) return;

        String sql = "INSERT IGNORE INTO ponto_material (id_ponto, id_material) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (TipoMaterial m : ponto.getMateriais()) {
                ps.setInt(1, ponto.getId());
                ps.setInt(2, m.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void removerMateriais(Connection conn, int idPonto) throws SQLException {
        String sql = "DELETE FROM ponto_material WHERE id_ponto = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPonto);
            ps.executeUpdate();
        }
    }
}
