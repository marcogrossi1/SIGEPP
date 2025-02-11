package proj.model;

import java.time.LocalDateTime;

public class Candidatura {

    private Long id;
    private Aluno candidato;
    private Long IDoportunidade;
    private String mensagem;
    private LocalDateTime dataAplicacao;
    private StatusCandidatura status; 

    public Long getIDoportunidade() {
        return IDoportunidade;
    }
    
    public Long getAlunoId() {
    	return candidato.getId();
    }
    public void setIDoportunidade(Long iDoportunidade) {
        IDoportunidade = iDoportunidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getCandidato() {
        return candidato;
    }

    public void setCandidato(Aluno candidato) {
        this.candidato = candidato;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public StatusCandidatura getStatus() {
        return status;
    }

    public void setStatus(StatusCandidatura status) {
        this.status = status;
    }

    public void setStatusFromDescricao(String descricao) {
        this.status = StatusCandidatura.fromDescricao(descricao); 
    }
    
    private String fotoPerfilBase64;

    public String getFotoPerfilBase64() {
        return fotoPerfilBase64;
    }

    public void setFotoPerfilBase64(String fotoPerfilBase64) {
        this.fotoPerfilBase64 = fotoPerfilBase64;
    }

}
