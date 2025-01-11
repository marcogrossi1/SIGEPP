package proj.model;

import jakarta.persistence.*;

@Entity
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeArquivo;

    @Lob
    private byte[] conteudo;

    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    	}
    
    public String getNomeArquivo() { 
    	return nomeArquivo; 
    	}
    
    public void setNomeArquivo(String nomeArquivo) { 
    	this.nomeArquivo = nomeArquivo; }
    
    public byte[] getConteudo() { return conteudo; 
    }
    
    public void setConteudo(byte[] conteudo) {
    	this.conteudo = conteudo; 
    	}
}