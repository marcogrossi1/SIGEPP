package proj.model;

public class Professor {

	private long id;
	private String nome;
	private long usuario_id;
	
	
	
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
	public long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}
	@Override
	public String toString() {
		return "Professor [id=" + id + ", nome=" + nome + ", usuario_id=" + usuario_id + "]";
	}
	
	
	
}
