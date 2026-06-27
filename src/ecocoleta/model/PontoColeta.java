package ecocoleta.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo que representa um Ponto de Coleta.
 */
public class PontoColeta {

 
    private int    id;
    private String nome;
    private String endereco;
    private double latitude;
    private double longitude;
    private String descricao;
    private boolean ativo;
    private int    idResponsavel;


    private List<TipoMaterial> materiais = new ArrayList<>();


    public PontoColeta() {}

    public PontoColeta(String nome, String endereco, String descricao, int idResponsavel) {
        this.nome          = nome;
        this.endereco      = endereco;
        this.descricao     = descricao;
        this.idResponsavel = idResponsavel;
        this.ativo         = true;
    }

   
    public int getId()                        { return id; }
    public void setId(int id)                 { this.id = id; }

    public String getNome()                   { return nome; }
    public void setNome(String nome)          { this.nome = nome; }

    public String getEndereco()               { return endereco; }
    public void setEndereco(String e)         { this.endereco = e; }

    public double getLatitude()               { return latitude; }
    public void setLatitude(double lat)       { this.latitude = lat; }

    public double getLongitude()              { return longitude; }
    public void setLongitude(double lon)      { this.longitude = lon; }

    public String getDescricao()              { return descricao; }
    public void setDescricao(String d)        { this.descricao = d; }

    public boolean isAtivo()                  { return ativo; }
    public void setAtivo(boolean ativo)       { this.ativo = ativo; }

    public int getIdResponsavel()             { return idResponsavel; }
    public void setIdResponsavel(int id)      { this.idResponsavel = id; }

    public List<TipoMaterial> getMateriais()  { return materiais; }
    public void setMateriais(List<TipoMaterial> m) { this.materiais = m; }

 
    @Override
    public String toString() {
        return String.format("PontoColeta[id=%d, nome=%s, endereco=%s, ativo=%b]",
                id, nome, endereco, ativo);
    }
}
