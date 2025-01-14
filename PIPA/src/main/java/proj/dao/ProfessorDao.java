package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import proj.model.Professor;

@Repository
public class ProfessorDao extends AbstractDaoBase {

    private final static String getByUsuario_idSql = "SELECT * FROM professor  WHERE usuario_id = ?";


    static Professor set(ResultSet rs) throws SQLException {
        Professor vo = new Professor();
        vo.setId(rs.getLong("id"));
        vo.setNome(rs.getString("nome"));
        vo.setUsuario_id(rs.getLong("usuario_id"));
        return vo;
    }

    public static Professor getByUsuario_id(Connection conn, long usuario_id) 
    throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByUsuario_idSql);
            ps.setLong(1, usuario_id);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + usuario_id + "]");}
            Professor b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }
}