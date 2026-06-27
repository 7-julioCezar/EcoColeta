package ecocoleta.dao;

import ecocoleta.model.TipoMaterial;
import ecocoleta.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TipoMaterialDAO {

    public List<TipoMaterial> listarTodos() {
        List<TipoMaterial> lista = new ArrayList<>();
        String sql = "SELECT * FROM tipos_material ORDER BY nome";

        try (Connection conn = ConexaoDB.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TipoMaterial tm = new TipoMaterial();
                tm.setId(rs.getInt("id"));
                tm.setNome(rs.getString("nome"));
                lista.add(tm);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar materiais: " + e.getMessage());
        }
        return lista;
    }

    public boolean cadastrar(TipoMaterial material) {
        String sql = "INSERT INTO tipos_material (nome) VALUES (?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, material.getNome());
            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) material.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar material: " + e.getMessage());
        }
        return false;
    }
}
