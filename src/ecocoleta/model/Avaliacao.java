package ecocoleta.model;


public class Avaliacao {

    private int    id;
    private int    nota;       
    private String comentario;
    private int    idUsuario;
    private int    idPonto;

    public Avaliacao() {}

    public Avaliacao(int nota, String comentario, int idUsuario, int idPonto) {
        this.nota       = nota;
        this.comentario = comentario;
        this.idUsuario  = idUsuario;
        this.idPonto    = idPonto;
    }

    public int getId()                    { return id; }
    public void setId(int id)             { this.id = id; }

    public int getNota()                  { return nota; }
    public void setNota(int nota)         { this.nota = nota; }

    public String getComentario()         { return comentario; }
    public void setComentario(String c)   { this.comentario = c; }

    public int getIdUsuario()             { return idUsuario; }
    public void setIdUsuario(int id)      { this.idUsuario = id; }

    public int getIdPonto()               { return idPonto; }
    public void setIdPonto(int id)        { this.idPonto = id; }

    @Override
    public String toString() {
        return String.format("Avaliacao[id=%d, nota=%d, idPonto=%d, idUsuario=%d]",
                id, nota, idPonto, idUsuario);
    }
}
