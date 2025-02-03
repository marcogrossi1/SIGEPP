package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import proj.model.Relatorio;

public class RelatorioDao {

    public static void insert(Connection conn, Relatorio relatorio) throws SQLException {
        String sql = "INSERT INTO relatorio (id_aluno, id_estagio, arquivo) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, relatorio.getIdAluno());
            stmt.setLong(2, relatorio.getIdEstagio());
            stmt.setBytes(3, relatorio.getArquivo());
            stmt.executeUpdate();
        }
    }
}