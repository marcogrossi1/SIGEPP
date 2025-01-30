package proj.ui.model;

public class PesquisaFiltrosAluno {
	
	String curso;
	String campus;
	String periodo;
	
	
	
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
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	@Override
	public String toString() {
		return "PesquisaFiltrosAluno [curso=" + curso + ", campus=" + campus + ", periodo=" + periodo + "]";
	}
	


}
