package proj.dao;
/*EM TESTES / DESATIVADO


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import proj.model.Secao;

public class SecaoDAO {
    private Connection connection;

    public SecaoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Secao> buscarPorUsuarioId(int usuarioId) throws SQLException {
        List<Secao> secoes = new ArrayList<>();
        String sql = "SELECT * FROM secoes WHERE usuario_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Secao secao = new Secao();
                secao.setId(rs.getInt("id"));
                secao.setUserId(rs.getInt("usuario_id"));
                secao.setTitulo(rs.getString("titulo"));
                secao.setTipo(rs.getString("tipo"));
                secoes.add(secao);
            }
        }
        return secoes;
    }

    public void inserir(Secao secao) throws SQLException {
        String sql = "INSERT INTO secoes (usuario_id, titulo, tipo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, secao.getUserId());
            stmt.setString(2, secao.getTitulo());
            stmt.setString(3, secao.getTipo());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM secoes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}


*/