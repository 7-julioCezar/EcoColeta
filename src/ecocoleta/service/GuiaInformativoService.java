package ecocoleta.service;

import ecocoleta.dao.GuiaInformativoDAO;
import ecocoleta.model.GuiaInformativo;

import java.util.List;

/**
 * Serviço para Guias Informativos (RF-06).
 */
public class GuiaInformativoService {

    private final GuiaInformativoDAO dao = new GuiaInformativoDAO();

    public String cadastrar(String titulo, String conteudo, int idAutor) {
        if (titulo == null || titulo.trim().isEmpty())
            return "ERRO: Título não pode ser vazio.";

        if (conteudo == null || conteudo.trim().isEmpty())
            return "ERRO: Conteúdo não pode ser vazio.";

        GuiaInformativo guia = new GuiaInformativo(titulo.trim(), conteudo.trim(), idAutor);
        boolean ok = dao.cadastrar(guia);

        return ok
            ? "OK: Guia cadastrado! ID=" + guia.getId()
            : "ERRO: Falha ao salvar guia.";
    }

    public List<GuiaInformativo> listarTodos() {
        return dao.listarTodos();
    }

    public GuiaInformativo buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public String excluir(int id) {
        return dao.excluir(id) ? "OK: Guia excluído." : "ERRO: Guia não encontrado.";
    }
}
