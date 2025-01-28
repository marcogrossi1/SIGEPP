package proj.model;

import java.util.Date;

enum StatusEvento{
    ATIVO, 
    ARQUIVADO,
    RASCUNHO
}


public class EventoProjeto {
    
    private Projeto projeto;
    private Date dateExpiracao;
    private Date datePublicacao;
    private String mensagem;
    private StatusEvento status;

    public EventoProjeto(Projeto projeto, String mensagem) {

        this.projeto = projeto;
        this.mensagem = mensagem;
        this.status = StatusEvento.RASCUNHO;
        this.dateExpiracao = null;
        this.datePublicacao = null;
    }

    public EventoProjeto(Projeto projeto, String mensagem, StatusEvento status) {

        this.projeto = projeto;
        this.mensagem = mensagem;
        this.status = status;
        this.dateExpiracao = null;
        this.datePublicacao = null;
    }

    public Projeto getProjeto() {
        return projeto;
    }
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }
    public Date getDataExpiracao() {
        return dateExpiracao;
    }
    public void setDataExpiracao(Date dateExpiracao) {
        this.dateExpiracao = dateExpiracao;
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
    
}
