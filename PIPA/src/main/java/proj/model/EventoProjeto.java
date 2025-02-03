package proj.model;

import java.util.Date;

public class EventoProjeto {
    
    private long id;
    private long projeto_id;
    private Date dateExpiracao;
    private Date datePublicacao;
    private String mensagem;
    private StatusEvento status;
    private byte[] imagem;

    public EventoProjeto(){}

    public EventoProjeto(long projeto_id, String mensagem, Date datePublicacao, Date dateExpiracao) {

        this.projeto_id = projeto_id;
        this.mensagem = mensagem;
        this.status = StatusEvento.RASCUNHO;
        this.dateExpiracao = new Date(dateExpiracao.getTime());
        this.datePublicacao = new Date(datePublicacao.getTime());
    }

    public EventoProjeto(long projeto_id, String mensagem, StatusEvento status, Date datePublicacao, Date dateExpiracao) {

        this.projeto_id = projeto_id;
        this.mensagem = mensagem;
        this.status = status;
        this.dateExpiracao = new Date(dateExpiracao.getTime());
        this.datePublicacao = new Date(datePublicacao.getTime());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateExpiracao() {
        return dateExpiracao;
    }

    public String getMensagem() {
        return mensagem;
    }
        
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public StatusEvento getStatus() {
        return status;
    }
    public void setStatus(StatusEvento status) {
        this.status = status;
    }

    public Date getDatePublicacao() {
        return datePublicacao;
    }

    public long getProjeto_id() {
        return projeto_id;
    }

    public void setProjeto_id(long projeto_id) {
        this.projeto_id = projeto_id;
    }

    public void setDatePublicacao(Date datePublicacao) {
        this.datePublicacao = datePublicacao;
    }

    public void setDateExpiracao(Date dateExpiracao) {
        this.dateExpiracao = dateExpiracao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    
}
