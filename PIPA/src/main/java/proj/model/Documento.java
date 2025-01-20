package proj.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Documento implements Serializable {

    @EmbeddedId
    private DocumentoId id;

    private String nomeArquivo;

    @Lob
    private byte[] conteudo;

    public DocumentoId getId() {
        return id;
    }

    public void setId(DocumentoId id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }
}
