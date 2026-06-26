package ecocoleta.model;

/**
 * Modelo que representa um Tipo de Material aceito nos pontos de coleta.
 * Exemplos: Plástico, Vidro, Eletrônico, Medicamento...
 */
public class TipoMaterial {

    private int    id;
    private String nome;

    public TipoMaterial() {}

    public TipoMaterial(String nome) {
        this.nome = nome;
    }

    public int getId()              { return id; }
    public void setId(int id)       { this.id = id; }

    public String getNome()         { return nome; }
    public void setNome(String n)   { this.nome = n; }

    @Override
    public String toString() {
        return String.format("TipoMaterial[id=%d, nome=%s]", id, nome);
    }
}
