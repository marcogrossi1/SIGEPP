package proj.model;

import java.util.ArrayList;

public class Empresa {

	private long id;
	private long usuario_id;
	private String nome;
	private String cnpj;
	private String area;
	private String email;
	private String senha;
	private String endereco;
	private String website;
	private String telefone;
	private ArrayList<Estagio> listaEstagios = new ArrayList<Estagio>();
	
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
		return "Empresa [id=" + id + ", nome=" + nome + ", usuario_id=" + usuario_id + "]";
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}	

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public ArrayList<Estagio> getListaEstagios() {
		return listaEstagios;
	}

	public void setListaEstagios(ArrayList<Estagio> listaEstagios) {
		this.listaEstagios = listaEstagios;
	}

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public void setEmail(String email) {
		this.email = email;
	}	

	public String getEmail() {
		return email;
	}

	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}