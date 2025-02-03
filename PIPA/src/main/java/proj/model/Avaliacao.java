package proj.model;

import java.sql.Timestamp;

public class Avaliacao {
    private Long id;
    private Long usuarioId;
    private Long perfilId;
    private String comentario;
    private Timestamp data;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getPerfilId() { return perfilId; }
    public void setPerfilId(Long perfilId) { this.perfilId = perfilId; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Timestamp getData() { return data; }
    public void setData(Timestamp data) { this.data = data; }
    private String nomeUsuario;

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }


}
