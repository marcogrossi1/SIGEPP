
package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;
import proj.model.Administrador;

public class AdministradorDao {

    private final static String getsql = "SELECT * FROM administrador WHERE id = ?";
    private final static String getByCpfSql = "SELECT * FROM administrador WHERE cpf = ?";
    private final static String getByEmailSql = "SELECT * FROM administrador WHERE email = ?";
    private final static String getByUsuario_idSql = "SELECT * FROM administrador WHERE usuario_id = ?";
    private final static String listsql = "SELECT * FROM administrador";
    private final static String listByNomeSql = "SELECT * FROM administrador WHERE nome = ? ";
    private final static String listByCampusSql = "SELECT * FROM administrador WHERE campus = ? ";
    private final static String insertsql = "INSERT INTO administrador (id, cpf, nome, campus, email, Usuario_id) VALUES(?, ?, ?, ?, ?, ?) ";
    private final static String updatesql = "UPDATE administrador SET id = ?, cpf = ?, nome = ?, campus = ?, email = ?, Usuario_id = ?, WHERE id = ? ";
    private final static String updateForCpfSql = "UPDATE administrador SET cpf = ?  WHERE id = ? ";
    private final static String updateForNomeSql = "UPDATE administrador SET nome = ?  WHERE id = ? ";
    private final static String updateForCampusSql = "UPDATE administrador SET campus = ?  WHERE id = ? ";
    private final static String updateForEmailSql = "UPDATE administrador SET email = ?  WHERE id = ? ";
    private final static String updateForUsuario_idSql = "UPDATE administrador SET usuario_id = ?  WHERE id = ? ";
    private final static String deletesql = "DELETE FROM administrador WHERE id = ?";

    private final static String listProjetosSql = "SELECT * FROM projeto";
	private final static String listEstagiosSql = "SELECT * FROM estagio";

    private static void closeResource(Statement ps) {
        try{if (ps != null) ps.close();}catch (Exception e){ps = null;}
    }

    private static void closeResource(Statement ps, ResultSet rs) {
        try{if (rs != null) rs.close();}catch (Exception e){rs = null;}
        try{if (ps != null) ps.close();}catch (Exception e){ps = null;}
    }

    //id, cpf, nome, campus, email, Usuario_id
    static Administrador set(ResultSet rs)
        throws SQLException
    {
        Administrador vo = new Administrador();
        vo.setId(rs.getLong("id"));
        vo.setCpf(rs.getString("cpf"));
        vo.setNome(rs.getString("nome"));
        vo.setCampus(rs.getString("campus"));
        vo.setEmail(rs.getString("email"));
        vo.setUsuario_id(rs.getLong("usuario_id"));
        return vo;
    }

    public static Administrador get(Connection conn, long id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found [" + id + "]");}
            Administrador b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static Administrador getByCpf(Connection conn, String cpf)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByCpfSql);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + cpf + "]");}
            Administrador b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static Administrador getByEmail(Connection conn, String email)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByEmailSql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + email + "]");}
            Administrador b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static Administrador getByUsuario_id(Connection conn, long usuario_id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByUsuario_idSql);
            ps.setLong(1, usuario_id);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + usuario_id + "]");}
            Administrador b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<Administrador> list(Connection conn)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<Administrador>();}
            ArrayList<Administrador> list = new ArrayList<Administrador>();
            do
            { Administrador b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<Administrador> listByNome(Connection conn, String nome)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<Administrador>();}
            ArrayList<Administrador> list = new ArrayList<Administrador>();
            do
            { Administrador b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<Administrador> listByCampus(Connection conn, String campus)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByCampusSql);
            ps.setString(1, campus);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<Administrador>();}
            ArrayList<Administrador> list = new ArrayList<Administrador>();
            do
            { Administrador b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static void insert(Connection conn, Aluno vo)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getCpf());
            ps.setString(2, vo.getNome());
            ps.setString(3, vo.getCampus());
            ps.setString(4, vo.getEmail());
            ps.setLong(5, vo.getUsuario_id());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
            long id = rs.getLong(1);
            vo.setId(id);
            }else { throw new SQLException("Nao foi possivel recuperar a CHAVE gerada na criacao do registro no banco de dados");} 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static void update(Connection conn, Aluno vo)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getCpf());
            ps.setString(2, vo.getNome());
            ps.setString(3, vo.getCampus());
            ps.setString(4, vo.getEmail());
            ps.setLong(5, vo.getUsuario_id());
            ps.setLong(6, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ vo.getId()+"] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForCpf(Connection conn, long id, String cpf)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForCpfSql);
            ps.setString(1, cpf);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForNome(Connection conn, long id, String nome)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForNomeSql);
            ps.setString(1, nome);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForCampus(Connection conn, long id, String campus)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForCampusSql);
            ps.setString(1, campus);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForEmail(Connection conn, long id, String email)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForEmailSql);
            ps.setString(1, email);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForUsuario_id(Connection conn, long id, long usuario_id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForUsuario_idSql);
            ps.setLong(1, usuario_id);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void delete(Connection conn, long id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletesql);
            ps.setLong(1,id);
            int count = ps.executeUpdate();
            if (count == 0 ){throw new NotFoundException("Object not found ["+id+"] .");}
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static ArrayList<Projeto> listProjetos(Connection conn) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listProjetosSql);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Projeto>();
			}
			ArrayList<Projeto> list = new ArrayList<Projeto>();
			do {
				Projeto b = ProjetoDao.set(rs);
				list.add(b);
			} while (rs.next());
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}
	
	
	public static ArrayList<Estagio> listEstagios(Connection conn) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listEstagiosSql);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Estagio>();
			}
			ArrayList<Estagio> list = new ArrayList<Estagio>();
			do {
				Estagio b = EstagioDao.set(rs);
				list.add(b);
			} while (rs.next());
			return list;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}
}
