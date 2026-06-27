package ecocoleta.service;

import ecocoleta.dao.AvaliacaoDAO;
import ecocoleta.model.Avaliacao;

import java.util.List;

/**
 * Serviço para Avaliações de pontos de coleta (RF-07).
 */
public class AvaliacaoService {

    private final AvaliacaoDAO dao = new AvaliacaoDAO();

 
    public String avaliar(int nota, String comentario, int idUsuario, int idPonto) {

        if (nota < 1 || nota > 5)
            return "ERRO: Nota deve ser entre 1 e 5.";

        if (idUsuario <= 0 || idPonto <= 0)
            return "ERRO: Usuário ou ponto inválido.";

        Avaliacao avaliacao = new Avaliacao(nota, comentario, idUsuario, idPonto);
        boolean ok = dao.cadastrar(avaliacao);

        return ok
            ? "OK: Avaliação registrada! ID=" + avaliacao.getId()
            : "ERRO: Falha ao salvar avaliação.";
    }

  
    public List<Avaliacao> listarPorPonto(int idPonto) {
        return dao.listarPorPonto(idPonto);
    }


    public String mediaPorPonto(int idPonto) {
        double media = dao.mediaPorPonto(idPonto);
        return String.format("Média do ponto %d: %.1f / 5.0", idPonto, media);
    }
}
