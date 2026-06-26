package ecocoleta.model;

/**
 * Modelo que representa um Guia Informativo sobre descarte correto (RF-06).
 */
public class GuiaInformativo {

    private int    id;
    private String titulo;
    private String conteudo;
    private int    idAutor;

    public GuiaInformativo() {}

    public GuiaInformativo(String titulo, String conteudo, int idAutor) {
        this.titulo   = titulo;
        this.conteudo = conteudo;
        this.idAutor  = idAutor;
    }

    public int getId()                  { return id; }
    public void setId(int id)           { this.id = id; }

    public String getTitulo()           { return titulo; }
    public void setTitulo(String t)     { this.titulo = t; }

    public String getConteudo()         { return conteudo; }
    public void setConteudo(String c)   { this.conteudo = c; }

    public int getIdAutor()             { return idAutor; }
    public void setIdAutor(int id)      { this.idAutor = id; }

    @Override
    public String toString() {
        return String.format("GuiaInformativo[id=%d, titulo=%s]", id, titulo);
    }
}
