package proj.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proj.model.Avaliacao;

public class AvaliacaoDao {

    public static void salvarAvaliacao(Connection conn, Avaliacao avaliacao) throws SQLException {
        String sql = "INSERT INTO Avaliacao (usuario_id, perfil_id, comentario) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, avaliacao.getUsuarioId());
            stmt.setLong(2, avaliacao.getPerfilId());
            stmt.setString(3, avaliacao.getComentario());
            stmt.executeUpdate();
        }
    }

    public static List<Avaliacao> listarAvaliacoesPorPerfil(Connection conn, Long perfilId) throws SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM Avaliacao WHERE perfil_id = ? ORDER BY data DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, perfilId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Avaliacao a = new Avaliacao();
                a.setId(rs.getLong("id"));
                a.setUsuarioId(rs.getLong("usuario_id"));
                a.setPerfilId(rs.getLong("perfil_id"));
                a.setComentario(rs.getString("comentario"));
                a.setData(rs.getTimestamp("data"));
                avaliacoes.add(a);
            }
        }
        return avaliacoes;
    }
    
    
}
