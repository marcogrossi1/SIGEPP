package proj.model;

import java.util.ArrayList;

public class Projeto {

	private long id;
	private String nome;
	private String responsavel;
	private String descricao;
	private int cargaHoraria;
	private int vagasRemuneradas;
	private String valorBolsa;
	private int vagasVoluntarias;
	private String requisito;
	private TipoProjeto tipoProjeto;
	private String campus;

	public enum TipoProjeto {
		EXTENSAO("Extensão"),
		MONITORIA("Monitoria"),
		PESQUISA("Pesquisa");

		private final String nomeTipo;

		TipoProjeto(String nomeTipo) {
			this.nomeTipo = nomeTipo;
		}

		public String getnomeTipo() {
			return nomeTipo;
		}
	}

	@Override
    public String toString() {
        return "Projeto [id=" + id + ", nome=" + nome + ", responsavel=" + responsavel + ", descricao=" + descricao
                + ", cargaHoraria=" + cargaHoraria + ", vagasRemuneradas=" + vagasRemuneradas + ", vagasVoluntarias=" + vagasVoluntarias
                + ", requisito=" + requisito + ", tipoProjeto=" + tipoProjeto.getnomeTipo() + "]";
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

	public String getValorBolsa() {
		return valorBolsa;
	}

	public void setValorBolsa(String valorBolsa) {
		this.valorBolsa = valorBolsa;
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

	public TipoProjeto getTipoProjeto() {
		return tipoProjeto;
	}
	
	public void setTipoProjeto(TipoProjeto tipoProjeto) {
		this.tipoProjeto = tipoProjeto;
	}
}