package ecocoleta.model;

/**
 * Modelo que representa um Usuário do sistema EcoColeta.
 * Tipos: CIDADAO, EMPRESA, ADMIN
 */
public class Usuario {

    // -------------------------------------------------------
    //  Atributos
    // -------------------------------------------------------
    private int    id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;   // "CIDADAO" | "EMPRESA" | "ADMIN"
    private boolean ativo;

    // -------------------------------------------------------
    //  Construtores
    // -------------------------------------------------------
    public Usuario() {}

    public Usuario(String nome, String email, String senha, String tipo) {
        this.nome  = nome;
        this.email = email;
        this.senha = senha;
        this.tipo  = tipo;
        this.ativo = true;
    }

    // -------------------------------------------------------
    //  Getters e Setters
    // -------------------------------------------------------
    public int getId()                { return id; }
    public void setId(int id)         { this.id = id; }

    public String getNome()           { return nome; }
    public void setNome(String nome)  { this.nome = nome; }

    public String getEmail()              { return email; }
    public void setEmail(String email)    { this.email = email; }

    public String getSenha()              { return senha; }
    public void setSenha(String senha)    { this.senha = senha; }

    public String getTipo()               { return tipo; }
    public void setTipo(String tipo)      { this.tipo = tipo; }

    public boolean isAtivo()              { return ativo; }
    public void setAtivo(boolean ativo)   { this.ativo = ativo; }

    // -------------------------------------------------------
    //  toString  (útil para exibir no console / depurar)
    // -------------------------------------------------------
    @Override
    public String toString() {
        return String.format("Usuario[id=%d, nome=%s, email=%s, tipo=%s, ativo=%b]",
                id, nome, email, tipo, ativo);
    }
}
