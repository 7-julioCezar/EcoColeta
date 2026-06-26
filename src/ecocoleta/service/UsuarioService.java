package ecocoleta.service;

import ecocoleta.dao.UsuarioDAO;
import ecocoleta.model.Usuario;

import java.util.List;

/**
 * Camada de Serviço para regras de negócio relacionadas ao Usuário.
 *
 * RF-01: Cadastro de usuários
 * RF-02: Login e autenticação
 */
public class UsuarioService {

    private final UsuarioDAO dao = new UsuarioDAO();

    
    public String cadastrar(String nome, String email, String senha, String tipo) {

        // Validações básicas
        if (nome == null || nome.trim().isEmpty())
            return "ERRO: Nome não pode ser vazio.";

        if (email == null || !email.contains("@"))
            return "ERRO: E-mail inválido.";

        if (senha == null || senha.length() < 4)
            return "ERRO: Senha deve ter no mínimo 4 caracteres.";

        if (!tipo.equals("CIDADAO") && !tipo.equals("EMPRESA") && !tipo.equals("ADMIN"))
            return "ERRO: Tipo inválido. Use CIDADAO, EMPRESA ou ADMIN.";

        // Verifica se e-mail já está cadastrado
        if (dao.buscarPorEmail(email) != null)
            return "ERRO: E-mail já cadastrado.";

        // Cria e salva o usuário
        Usuario usuario = new Usuario(nome.trim(), email.toLowerCase().trim(), senha, tipo);
        boolean ok = dao.cadastrar(usuario);

        return ok
            ? "OK: Usuário cadastrado com sucesso! ID=" + usuario.getId()
            : "ERRO: Falha ao salvar no banco de dados.";
    }

    // ------------------------------------------------------------------
    //  RF-02: Login — retorna o usuário ou null
    // ------------------------------------------------------------------
    public Usuario login(String email, String senha) {
        if (email == null || senha == null) return null;
        return dao.login(email.toLowerCase().trim(), senha);
    }

    // ------------------------------------------------------------------
    //  Listar todos
    // ------------------------------------------------------------------
    public List<Usuario> listarTodos() {
        return dao.listarTodos();
    }

    // ------------------------------------------------------------------
    //  Buscar por ID
    // ------------------------------------------------------------------
    public Usuario buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    // ------------------------------------------------------------------
    //  Desativar usuário
    // ------------------------------------------------------------------
    public String desativar(int id) {
        boolean ok = dao.desativar(id);
        return ok ? "OK: Usuário desativado." : "ERRO: Usuário não encontrado.";
    }
}
