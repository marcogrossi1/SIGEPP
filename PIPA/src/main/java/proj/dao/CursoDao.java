package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import proj.model.Curso;

public class CursoDao {
    private final static String getsql = "SELECT * FROM Curso  WHERE id = ?";

    static Curso set(ResultSet rs) throws SQLException {
        Curso vo = new Curso();
        vo.setId(rs.getInt("id"));
        vo.setNomeCurso(rs.getString("nome"));
        return vo;
    }

    public static Curso get(Connection conn, int id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (!rs.next())
                throw new NotFoundException("Curso não encontrado");
            Curso c = set(rs);
            return c;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar curso", e);
        } finally {
            closeResource(ps, rs);
        }
    }

    protected static void closeResource(Statement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception e) {
            rs = null;
        }

        try {
            if (ps != null)
                ps.close();
        } catch (Exception e) {
            ps = null;
        }
    }
}