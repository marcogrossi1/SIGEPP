package proj.model;

public class Projeto {

	private long id;
	private String nome;
	private String responsavel;
	private String descricao;
	private int cargaHoraria;
	private int vagasRemuneradas;
	private int vagasVoluntarias;
	private String requisito;
	TipoProjeto tipoProjeto;
	private String campus;
	private Curso curso;

	public enum TipoProjeto {
		EXTENSAO("Extensão"),
		MONITORIA("Monitoria"),
		PESQUISA("Pesquisa");

		private final String descricao;

		TipoProjeto(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}

	public enum Curso {
		TECNICO_INFORMATICA("Técnico em Informática"),
		TECNICO_REDES_DE_COMPUTADORES("Técnico em Redes de Computadores"),
		TECNICO_EDIFICACOES("Técnico em Edificações"),
		TECNICO_ELETRONICA("Técnico em Eletrônica"),
		TECNICO_ELETROTECNICA("Técnico em Eletrotécnica"),
		TECNICO_EQUIPAMENTOS_BIOMEDICOS("Técnico em Equipamentos Biomédicos"),
		TECNICO_MECANICA("Técnico em Mecânica"),
		TECNICO_MECATRONICA("Técnico em Mecatrônica"),
		TECNICO_QUIMICA("Técnico em Química"),
		TECNICO_HOSPEDAGEM("Técnico em Hospedagem"),
		TECNICO_ESTRADAS("Técnico em Estradas"),
		TECNICO_MEIO_AMBIENTE("Técnico em Meio Ambiente"),
		TECNICO_TRANSITO("Técnico em Trânsito");
	
		private final String descricao;
	
		Curso(String descricao) {
			this.descricao = descricao;
		}
	
		public String getDescricao() {
			return descricao;
		}
	}

	@Override
    public String toString() {
        return "Projeto [id=" + id + ", nome=" + nome + ", responsavel=" + responsavel + ", descricao=" + descricao
                + ", cargaHoraria=" + cargaHoraria + ", vagasRemuneradas=" + vagasRemuneradas + ", vagasVoluntarias=" + vagasVoluntarias
                + ", requisito=" + requisito + ", tipoProjeto=" + tipoProjeto.getDescricao() + ", curso=" + curso.getDescricao() + "]";
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

	public int getVagasRemuneradas() {
		return vagasRemuneradas;
	}

	public void setVagasRemuneradas(int vagasRemuneradas) {
		this.vagasRemuneradas = vagasRemuneradas;
	}

	public int getVagasVoluntarias() {
		return vagasVoluntarias;
	}

	public void setVagasVoluntarias(int vagasVoluntarias) {
		this.vagasVoluntarias = vagasVoluntarias;
	}

	public String getRequisito() {
		return requisito;
	}

	public void setRequisito(String requisito) {
		this.requisito = requisito;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public Curso getCurso() {
		return curso;
	}
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public TipoProjeto getTipoProjeto() {
		return tipoProjeto;
	}
	
	public void setTipoProjeto(TipoProjeto tipoProjeto) {
		this.tipoProjeto = tipoProjeto;
	}

}
