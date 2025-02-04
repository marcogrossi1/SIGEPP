package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proj.model.Relatorio;

public class RelatorioDao {

    private final static String insertsql = "INSERT INTO relatorio (idAluno, idEstagio, arquivo) VALUES (?, ?, ?)";

    public static void insert(Connection conn, Relatorio relatorio) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(insertsql);
            ps.setLong(1, relatorio.getIdAluno());
            ps.setLong(2, relatorio.getIdEstagio());
            ps.setBytes(3, relatorio.getArquivo());
            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
        }
    }
}