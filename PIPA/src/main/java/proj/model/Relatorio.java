package proj.model;
public class Relatorio {
    private Long id;
    private Long idAluno;
    private Long idEstagio;
    private byte[] arquivo;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Long getIdEstagio() {
        return idEstagio;
    }

    public void setIdEstagio(Long idEstagio) {
        this.idEstagio = idEstagio;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }
}