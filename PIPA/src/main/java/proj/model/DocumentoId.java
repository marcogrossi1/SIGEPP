package proj.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
 public class DocumentoId implements Serializable {
    private Long idAluno;
    private Long idProjeto;

    public DocumentoId() {}

    public DocumentoId(Long idAluno, Long idProjeto) {
        this.idAluno = idAluno;
        this.idProjeto = idProjeto;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Long getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Long idProjeto) {
        this.idProjeto = idProjeto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoId that = (DocumentoId) o;
        return Objects.equals(idAluno, that.idAluno) &&
               Objects.equals(idProjeto, that.idProjeto);
    }
    @Override
    public int hashCode() {
        return Objects.hash(idAluno, idProjeto);
    }
}