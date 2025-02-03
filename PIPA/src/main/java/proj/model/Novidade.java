package proj.model;

public class Novidade {
    private String nome;
    private String descricao;
    private boolean isEstagio;
    private String link;

    public Novidade(String nome, String descricao, boolean isEstagio, long id) {
        this.nome = nome;
        this.descricao = descricao;
        this.isEstagio = isEstagio;
        this.link = gerarLink(id);
    }

    public Novidade(String nome, String descricao, boolean isEstagio, String link) {
        this.nome = nome;
        this.descricao = descricao;
        this.isEstagio = isEstagio;
        this.link = link;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isEstagio() {
        return isEstagio;
    }
    public void setEstagio(boolean isEstagio) {
        this.isEstagio = isEstagio;
    }

    public String getLink() {
        return link;
    }
    private String gerarLink(long id) {
        return isEstagio 
            ? "aluno/detalhes-estagio?n=" + id 
            : "aluno/detalhes-projeto?id=" + id;
    }
}
