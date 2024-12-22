package proj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Projeto {
	
	@Id
	@GeneratedValue
	private int id;
	private String nome;
	private String responsavel;
	private String descricao;
	private int carga_horaria;
	private int vagas;
	private String requisito;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getCarga_horaria() {
		return carga_horaria;
	}
	public void setCarga_horaria(int carga_horaria) {
		this.carga_horaria = carga_horaria;
	}
	
	public int getVagas() {
		return vagas;
	}
	public void setVagas(int vagas) {
		this.vagas = vagas;
	}
	
	public String getRequisito() {
		return requisito;
	}
	public void setRequisito(String requisito) {
		this.requisito = requisito;
	}

}
