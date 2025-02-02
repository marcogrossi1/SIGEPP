package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

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
                throw new NotFoundException("Curso não encontrado: " + id);
            Curso c = set(rs);
            return c;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar curso", e);
        } finally {
            closeResource(ps, rs);
        }
    }

    public static int getIdPorNomeCurso(Connection conn, String nome) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM Curso WHERE nome = ?")) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new NotFoundException("Curso não encontrado: " + nome);
                }
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar curso por nome", e);
        }
    }

    public static HashSet<Curso> listCursos(Connection conn) throws SQLException {
    HashSet<Curso> cursos = new HashSet<>();
    String sql = "SELECT * FROM Curso";
    try (PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            cursos.add(set(rs));
        }
    } catch (SQLException e) {
        throw new SQLException("Erro ao buscar todos os cursos", e);
    }
    return cursos;
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