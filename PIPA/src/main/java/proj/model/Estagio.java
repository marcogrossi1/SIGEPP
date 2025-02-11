package proj.model;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Estagio {
	
	@Id
	private long id;
	private String empresa;
	private String descricao;
	private int cargaHoraria;
	private int vagas;
	private String requisito;
	private String salario;
        private String documentos;

	@Override
	public String toString() {
		return "Estagio [id=" + id + ", empresa=" + empresa + ", descricao=" + descricao + ", cargaHoraria="
				+ cargaHoraria + ", vagas=" + vagas + ", requisito=" + requisito + ", salario="+ salario + "documentos="+documentos+ "]";
	}
        public Estagio(long id, String empresa, String descricao, int cargaHoraria, int vagas, String requisito, String salario, String documentos){
            setId(id);
            setEmpresa(empresa);
            setDescricao(descricao);
            setCargaHoraria(cargaHoraria);
            setVagas(vagas);
            setRequisito(requisito);
            setSalario(salario);
            setDocumentos(documentos);
        }
        public Estagio(){}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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
	public String getSalario() {
		return salario;
	}
	public void setSalario(String salario) {
		this.salario = salario;
	}
        public String getDocumentos(){
            return documentos;
        }
        public void setDocumentos(String documentos){
            this.documentos = documentos;
        }
}