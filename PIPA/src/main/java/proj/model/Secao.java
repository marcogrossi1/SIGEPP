package proj.model;

public class Secao {
    private Long id;
    private Long usuarioId;
    private String titulo;
    private String tipo;
    private String conteudoTexto;
    private Integer comprimentoConteudoTexto;
    private Integer alturaConteudoTexto;
    private byte[] conteudoImagem;
    private int ordem;
	private Integer topConteudoTexto;
	private Integer leftConteudoTexto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
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

	public void setTopConteudoTexto(Integer topConteudoTexto) {
		this.topConteudoTexto = topConteudoTexto;
	}
	
	public Integer getTopConteudoTexto() {
		return topConteudoTexto;
	}

	public void setLeftConteudoTexto(Integer leftConteudoTexto) {
		this.leftConteudoTexto = leftConteudoTexto;
	}
	
	public Integer getLeftConteudoTexto() {
		return leftConteudoTexto;
	}
}
