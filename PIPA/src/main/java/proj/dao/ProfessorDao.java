package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import proj.model.Professor;

public class ProfessorDao {

    private final static String getsql = "SELECT * FROM Professor WHERE id = ?";
    private final static String getByUsuario_idSql = "SELECT * FROM Professor WHERE usuario_id = ?";
    private final static String listsql = "SELECT * FROM Professor";
    private final static String listByNomeSql = "SELECT * FROM Professor WHERE nome = ?";
    private final static String insertsql = "INSERT INTO professor (cpf, nome, email, telefone, fotoPerfil, bannerPerfil, descricaoPerfil, usuario_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private final static String updatesql = "UPDATE Professor SET cpf = ?, nome = ?, email = ?, telefone = ?, fotoPerfil = ?, bannerPerfil = ?, descricaoPerfil = ?, usuario_id = ? WHERE id = ?";
    private final static String updateForNomeSql = "UPDATE Professor SET nome = ? WHERE id = ?";
    private final static String updateForUsuario_idSql = "UPDATE Professor SET usuario_id = ? WHERE id = ?";
    private final static String deletesql = "DELETE FROM Professor WHERE id = ?";

    private static void closeResource(Statement ps) {
        try { if (ps != null) ps.close(); } catch (Exception e) { ps = null; }
    }

    private static void closeResource(Statement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception e) { rs = null; }
        try { if (ps != null) ps.close(); } catch (Exception e) { ps = null; }
    }

    static Professor set(ResultSet rs) throws SQLException {
        Professor vo = new Professor();
        vo.setId(rs.getLong("id"));
        vo.setCpf(rs.getString("cpf"));
        vo.setNome(rs.getString("nome"));
        vo.setEmail(rs.getString("email"));
        vo.setTelefone(rs.getString("telefone"));
        vo.setFotoPerfil(rs.getBytes("fotoPerfil"));
        vo.setBannerPerfil(rs.getBytes("bannerPerfil"));
        vo.setDescricaoPerfil(rs.getString("descricaoPerfil"));
        vo.setUsuario_id(rs.getLong("usuario_id"));
        return vo;
    }

    public static Professor get(Connection conn, long id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) { throw new NotFoundException("Object not found [" + id + "]"); }
            Professor b = set(rs);
            return b;
        } catch (SQLException e) { throw e; }
        finally { closeResource(ps, rs); ps = null; rs = null; }
    }

    public static Professor getByUsuario_id(Connection conn, long usuario_id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByUsuario_idSql);
            ps.setLong(1, usuario_id);
            rs = ps.executeQuery();
            if (!rs.next()) { throw new NotFoundException("Object not found By [" + usuario_id + "]"); }
            Professor b = set(rs);
            return b;
        } catch (SQLException e) { throw e; }
        finally { closeResource(ps, rs); ps = null; rs = null; }
    }

    public static ArrayList<Professor> list(Connection conn) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) { return new ArrayList<Professor>(); }
            ArrayList<Professor> list = new ArrayList<Professor>();
            do { Professor b = set(rs); list.add(b); } while (rs.next());
            return list;
        } catch (SQLException e) { throw e; }
        finally { closeResource(ps, rs); ps = null; rs = null; }
    }

    public static ArrayList<Professor> listByNome(Connection conn, String nome) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) { return new ArrayList<Professor>(); }
            ArrayList<Professor> list = new ArrayList<Professor>();
            do { Professor b = set(rs); list.add(b); } while (rs.next());
            return list;
        } catch (SQLException e) { throw e; }
        finally { closeResource(ps, rs); ps = null; rs = null; }
    }

    public static void insert(Connection conn, Professor vo) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        // (cpf, nome, email, telefone, fotoPerfil, bannerPerfil, descricaoPerfil, usuario_id)
        try {
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getCpf());
            ps.setString(2, vo.getNome());
            ps.setString(3, vo.getEmail());
            ps.setString(4, vo.getTelefone());
            ps.setBytes(5, vo.getFotoPerfil());
            ps.setBytes(6, vo.getBannerPerfil());
            ps.setString(7, vo.getDescricaoPerfil());
            ps.setLong(8, vo.getUsuario_id());
            ps.executeUpdate();
            conn.commit();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long id = rs.getLong(1);
                vo.setId(id);
            } else { throw new SQLException("Nao foi possivel recuperar a CHAVE gerada na criacao do registro no banco de dados"); }
        } catch (SQLException e) { try { conn.rollback(); } catch (Exception e1) {} throw e; }
        finally { closeResource(ps, rs); ps = null; rs = null; }
    }

    public static void update(Connection conn, Professor vo) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getCpf());
            ps.setString(2, vo.getNome());
            ps.setString(3, vo.getEmail());
            ps.setString(4, vo.getTelefone());
            ps.setBytes(5, vo.getFotoPerfil());
            ps.setBytes(6, vo.getBannerPerfil());
            ps.setString(7, vo.getDescricaoPerfil());
            ps.setLong(8, vo.getUsuario_id());
            ps.setLong(9, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0) { throw new NotFoundException("Object not found [" + vo.getId() + "] ."); }
            //SEM COMMIT 
        } catch (SQLException e) { try { conn.rollback(); } catch (Exception e1) {} throw e; }
        finally { closeResource(ps); ps = null; }
    }

    public static void updateForNome(Connection conn, long id, String nome) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForNomeSql);
            ps.setString(1, nome);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) { throw new NotFoundException("Object not found [" + id + "] ."); }
            //SEM COMMIT 
        } catch (SQLException e) { try { conn.rollback(); } catch (Exception e1) {} throw e; }
        finally { closeResource(ps); ps = null; }
    }

    public static void updateForUsuario_id(Connection conn, long id, long usuario_id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForUsuario_idSql);
            ps.setLong(1, usuario_id);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0) { throw new NotFoundException("Object not found [" + id + "] ."); }
            //SEM COMMIT 
        } catch (SQLException e) { try { conn.rollback(); } catch (Exception e1) {} throw e; }
        finally { closeResource(ps); ps = null; }
    }

    public static void delete(Connection conn, long id) throws NotFoundException, SQLException {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletesql);
            ps.setLong(1, id);
            int count = ps.executeUpdate();
            if (count == 0 ){throw new NotFoundException("Object not found ["+id+"] .");}
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }
    
  /*  public static void delete(Connection conn, long id)
	        throws NotFoundException, SQLException
	    {
	    	Professor p = new Professor();
	    	Usuario u = new Usuario();
	    	String sql1 = "delete from professor_has_projeto where professor_id = ? ";
	    	String sql2 = "delete from professor where id = ? ";
	    	String sql3 = "delete from seguidores where seguidor_id = ? ";
	    	String sql4 = "delete from seguidores where seguindo_id = ? ";
	    	String sql5 = "delete from usuario where id = ? ";
	    	
	    	
	    	p = ProfessorDao.get(conn, id);
	    	
	    	System.out.println(p);
	    	
	    	u = UsuarioDao.get(conn, p.getUsuario_id());


	    	deleteRelation(conn, sql1, id);	
	    	deleteRelation(conn, sql2, id);	
	    	deleteRelation(conn, sql3, u.getId());
	    	deleteRelation(conn, sql4, u.getId());
	    	deleteRelation(conn, sql5, u.getId());
	    }

	    private static void deleteRelation(Connection conn, String sql, long id)
	    throws NotFoundException, SQLException
	    {
	        PreparedStatement ps = null;
	        try {
	            ps = conn.prepareStatement(sql);
	            ps.setLong(1,id);
	            ps.executeUpdate();
	        }
	        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
	        finally{closeResource(ps); ps = null; }
	    }

*/ 
}