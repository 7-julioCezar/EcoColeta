package ecocoleta.dao;

import ecocoleta.model.Usuario;
import ecocoleta.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) responsável pelas operações de banco de dados
 * relacionadas à entidade Usuario.
 *
 * RF-01: Cadastro de usuários (cidadãos, administradores e empresas)
 * RF-02: Login e autenticação
 */
public class UsuarioDAO {

    // ------------------------------------------------------------------
    //  CREATE — Cadastrar novo usuário (RF-01)
    // ------------------------------------------------------------------
    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, senha, tipo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());   // em produção, use hash!
            ps.setString(4, usuario.getTipo());

            int linhas = ps.executeUpdate();

            if (linhas > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  READ — Buscar por ID
    // ------------------------------------------------------------------
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------------------------------------------
    //  READ — Buscar por email (usado no login)
    // ------------------------------------------------------------------
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND ativo = TRUE";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
        }
        return null;
    }

    // ------------------------------------------------------------------
    //  READ — Login: validar email + senha (RF-02)
    // ------------------------------------------------------------------
    public Usuario login(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ? AND ativo = TRUE";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, senha);   // em produção, compare com hash!
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro no login: " + e.getMessage());
        }
        return null;   // retorna null se credenciais inválidas
    }

    // ------------------------------------------------------------------
    //  READ — Listar todos
    // ------------------------------------------------------------------
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY nome";

        try (Connection conn = ConexaoDB.getConexao();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return lista;
    }

    // ------------------------------------------------------------------
    //  UPDATE — Atualizar dados
    // ------------------------------------------------------------------
    public boolean atualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, tipo = ? WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTipo());
            ps.setInt(4, usuario.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  DELETE — Desativar usuário (soft delete)
    // ------------------------------------------------------------------
    public boolean desativar(int id) {
        String sql = "UPDATE usuarios SET ativo = FALSE WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao desativar usuário: " + e.getMessage());
        }
        return false;
    }

    // ------------------------------------------------------------------
    //  Método auxiliar — mapear ResultSet → Usuario
    // ------------------------------------------------------------------
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
        u.setTipo(rs.getString("tipo"));
        u.setAtivo(rs.getBoolean("ativo"));
        return u;
    }
}
