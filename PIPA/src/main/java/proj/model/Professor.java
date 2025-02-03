
package proj.model;

public class Professor {

	private long id;
	private String nome;
	private String email;
	private String telefone;
	private String cpf;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getTelefone() {
		return telefone;
	}
	public long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}
	@Override
	public String toString() {
		return "Professor [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone
				+ ", usuario_id=" + usuario_id + "]";
	}
	
	
	
}
