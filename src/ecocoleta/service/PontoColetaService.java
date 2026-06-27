package ecocoleta.service;

import ecocoleta.dao.PontoColetaDAO;
import ecocoleta.model.PontoColeta;
import ecocoleta.model.TipoMaterial;

import java.util.List;


public class PontoColetaService {

    private final PontoColetaDAO dao = new PontoColetaDAO();

   
    public String cadastrar(String nome, String endereco, String descricao,
                            int idResponsavel, List<TipoMaterial> materiais) {

        if (nome == null || nome.trim().isEmpty())
            return "ERRO: Nome do ponto não pode ser vazio.";

        if (endereco == null || endereco.trim().isEmpty())
            return "ERRO: Endereço não pode ser vazio.";

        if (idResponsavel <= 0)
            return "ERRO: Responsável inválido.";

        PontoColeta ponto = new PontoColeta(nome.trim(), endereco.trim(), descricao, idResponsavel);
        if (materiais != null) ponto.setMateriais(materiais);

        boolean ok = dao.cadastrar(ponto);
        return ok
            ? "OK: Ponto cadastrado com sucesso! ID=" + ponto.getId()
            : "ERRO: Falha ao salvar no banco de dados.";
    }


    public List<PontoColeta> listarTodos() {
        return dao.listarTodos();
    }

  
    public PontoColeta buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

  
    public List<PontoColeta> filtrarPorMaterial(int idMaterial) {
        return dao.filtrarPorMaterial(idMaterial);
    }


    public String atualizar(PontoColeta ponto) {
        if (ponto == null || ponto.getId() <= 0)
            return "ERRO: Ponto inválido.";

        boolean ok = dao.atualizar(ponto);
        return ok ? "OK: Ponto atualizado." : "ERRO: Ponto não encontrado.";
    }


    public String desativar(int id) {
        boolean ok = dao.desativar(id);
        return ok ? "OK: Ponto desativado." : "ERRO: Ponto não encontrado.";
    }
}
