package proj.dao;

import proj.model.Novidade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NovidadeDao {

    public static void adicionarNovidade(Connection conn, Novidade novidade) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO novidade (nome, descricao, isEstagio) VALUES (?, ?, ?)")) {
            ps.setString(1, novidade.getNome());
            ps.setString(2, novidade.getDescricao());
            ps.setBoolean(3, novidade.isEstagio());
            ps.executeUpdate();
        }
    }

    public static List<Novidade> listarNovidades(Connection conn) throws SQLException {
        List<Novidade> novidades = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Novidade WHERE dataPublicacao >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) ORDER BY dataPublicacao DESC;");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Novidade novidade = new Novidade(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getBoolean("isEstagio"),
                        rs.getString("link")
                );
                novidades.add(novidade);
            }
        }
        return novidades;
    }

    public static void removerNovidade(Connection conn, String nome) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM novidade WHERE nome = ?")) {
            ps.setString(1, nome);
            ps.executeUpdate();
        }
    }
}
