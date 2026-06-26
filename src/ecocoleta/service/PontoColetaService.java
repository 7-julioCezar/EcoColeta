package ecocoleta.service;

import ecocoleta.dao.PontoColetaDAO;
import ecocoleta.model.PontoColeta;
import ecocoleta.model.TipoMaterial;

import java.util.List;

/**
 * Camada de Serviço para Pontos de Coleta.
 *
 * RF-03: Visualizar pontos
 * RF-04: Cadastrar novos pontos
 * RF-05: Filtrar por tipo de material
 */
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

    // ------------------------------------------------------------------
    //  RF-03: Listar todos os pontos ativos
    // ------------------------------------------------------------------
    public List<PontoColeta> listarTodos() {
        return dao.listarTodos();
    }

    // ------------------------------------------------------------------
    //  Buscar por ID
    // ------------------------------------------------------------------
    public PontoColeta buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    // ------------------------------------------------------------------
    //  RF-05: Filtrar por tipo de material
    // ------------------------------------------------------------------
    public List<PontoColeta> filtrarPorMaterial(int idMaterial) {
        return dao.filtrarPorMaterial(idMaterial);
    }

    // ------------------------------------------------------------------
    //  Atualizar ponto
    // ------------------------------------------------------------------
    public String atualizar(PontoColeta ponto) {
        if (ponto == null || ponto.getId() <= 0)
            return "ERRO: Ponto inválido.";

        boolean ok = dao.atualizar(ponto);
        return ok ? "OK: Ponto atualizado." : "ERRO: Ponto não encontrado.";
    }

    // ------------------------------------------------------------------
    //  Desativar ponto
    // ------------------------------------------------------------------
    public String desativar(int id) {
        boolean ok = dao.desativar(id);
        return ok ? "OK: Ponto desativado." : "ERRO: Ponto não encontrado.";
    }
}
