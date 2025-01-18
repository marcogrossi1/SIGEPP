package proj.model;

public class Usuario {

	private long id;
	private String nome;
	private String senha;
	private String role;
	//private String descricao;
	//private String bannerUrl;
	//private String fotoPerfilUrl;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/* EM TESTE | DESATIVADO
	 * public String getDescricao() {
        return descricao;
    }
	
	public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
	
	public String getBannerUrl() {
        return bannerUrl;
    }
	
	public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
	
	public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }*/
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", role=" + role + "]";
	}
}
