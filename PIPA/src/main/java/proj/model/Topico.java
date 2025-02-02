package proj.model;

public class Topico {
    private long id;
    private Long secao_id;
    private String conteudoTexto;
    private byte[] conteudoImagem;
    private byte[] conteudoArquivo;
    private Integer comprimentoConteudoTexto;
    private Integer alturaConteudoTexto;
    private boolean estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSecaoId() {
        return secao_id;
    }
    
    public void setSecaoId(Long secao_id) {
        this.secao_id = secao_id;
    }
    
    public boolean getEstado() {
        return estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getConteudoTexto() {
        return conteudoTexto;
    }

    public void setConteudoTexto(String conteudoTexto) {
        this.conteudoTexto = conteudoTexto;
    }

    public byte[] getConteudoImagem() {
        return conteudoImagem;
    }

    public void setConteudoImagem(byte[] conteudoImagem) {
        this.conteudoImagem = conteudoImagem;
    }

    public byte[] getConteudoArquivo() {
        return conteudoArquivo;
    }

    public void setConteudoArquivo(byte[] conteudoArquivo) {
        this.conteudoArquivo = conteudoArquivo;
    }
    
    public Integer getComprimentoConteudoTexto() {
        return comprimentoConteudoTexto;
    }

    public void setComprimentoConteudoTexto(Integer comprimentoConteudoTexto) {
        this.comprimentoConteudoTexto = comprimentoConteudoTexto;
    }

    public Integer getAlturaConteudoTexto() {
        return alturaConteudoTexto;
    }

    public void setAlturaConteudoTexto(Integer alturaConteudoTexto) {
        this.alturaConteudoTexto = alturaConteudoTexto;
    }
}