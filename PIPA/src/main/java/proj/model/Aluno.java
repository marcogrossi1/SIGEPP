package proj.model;

import java.util.ArrayList;

public class Aluno{

	private long id;
	private String cpf;
	private String nome;
	private String curso;
	private String campus;
	private String email;
	private String periodo;
	private long   usuario_id;
	
	private ArrayList<Estagio> listaEstagio = new ArrayList<Estagio>();
	private ArrayList<Projeto> listaProjeto = new ArrayList<Projeto>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", cpf=" + cpf + ", nome=" + nome + ", curso=" + curso + ", campus=" + campus
				+ ", email=" + email + ", periodo=" + periodo + ", listaEstagio=" + listaEstagio
				+ ", listaProjeto=" + listaProjeto + "]";
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public ArrayList<Estagio> getListaEstagio() {
		return listaEstagio;
	}

	public void setListaEstagio(ArrayList<Estagio> listaEstagio) {
		this.listaEstagio = listaEstagio;
	}

	public ArrayList<Projeto> getListaProjeto() {
		return listaProjeto;
	}

	public void setListaProjeto(ArrayList<Projeto> listaProjeto) {
		this.listaProjeto = listaProjeto;
	}
	
	
	public long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}
}
