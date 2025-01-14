package proj.dao;
/*EM TESTES / DESATIVADO


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import proj.model.Topico;

public class TopicoDAO {
    private Connection connection;

    public TopicoDAO(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Topico topico) throws SQLException {
        String sql = "INSERT INTO topicos (secao_id, texto, imagem_url) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, topico.getSectionId());
            stmt.setString(2, topico.getTexto());
            stmt.setString(3, topico.getImagemUrl());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM topicos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
*/