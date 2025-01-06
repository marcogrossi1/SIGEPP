package proj.model;

public class Projeto {

	private long id;
	private String nome;
	private String responsavel;
	private String descricao;
	private int cargaHoraria;
	private int vagas;
	private String requisito;

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", nome=" + nome + ", responsavel=" + responsavel + ", descricao=" + descricao
				+ ", cargaHoraria=" + cargaHoraria + ", vagas=" + vagas + ", requisito=" + requisito + "]";
	}
	
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

	public int getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
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
