package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import proj.model.Estagio;

public class EstagioDao extends AbstractDaoBase {
	private final static String getsql = "SELECT * FROM Estagio WHERE id = ?";
	private final static String listsql = "SELECT * FROM Estagio";
	private final static String listByNomeSql = "SELECT * FROM Estagio WHERE nome like %?% ";
	private final static String insertsql = "INSERT INTO Estagio (empresa, descricao, carga_horaria, vagas, requisito, salario) VALUES( ?, ?, ?, ?, ?, ?) ";
	private final static String updatesql = "UPDATE estagio SET empresa = ?, descricao = ?, carga_horaria = ?, vagas = ?, requisito = ?, salario = ?, WHERE id = ? ";
	private final static String deletesql = "DELETE FROM estagio WHERE id = ?";

	static Estagio set(ResultSet rs) throws SQLException {
		Estagio vo = new Estagio();
		vo.setId(rs.getLong("id"));
		vo.setEmpresa(rs.getString("empresa"));
		vo.setDescricao(rs.getString("descricao"));
		vo.setCargaHoraria(rs.getInt("carga_horaria"));
		vo.setVagas(rs.getInt("vagas"));
		vo.setRequisito(rs.getString("requisito"));
		vo.setSalario(rs.getString("salario"));
		return vo;
	}

	public static Estagio get(Connection conn, long id) 
	throws NotFoundException, SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getsql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found [" + id + "]");
			}
			Estagio b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static ArrayList<Estagio> list(Connection conn) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listsql);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Estagio>();
			}
			ArrayList<Estagio> list = new ArrayList<Estagio>();
			do {
				Estagio b = set(rs);
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

	public static ArrayList<Estagio> listByNome(Connection conn, String nome) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listByNomeSql);
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Estagio>();
			}
			ArrayList<Estagio> list = new ArrayList<Estagio>();
			do {
				Estagio b = set(rs);
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

	public static void insert(Connection conn, Estagio vo) 
	throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(insertsql);
			ps.setString(1, vo.getEmpresa());
			ps.setString(2, vo.getDescricao());
			ps.setInt(3, vo.getCargaHoraria());
			ps.setInt(4, vo.getVagas());
			ps.setString(5, vo.getRequisito());
			ps.setString(6, vo.getSalario());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				long id = rs.getLong(1);
				vo.setId(id);
			} else {
				throw new SQLException(
						"Nao foi possivel recuperar a CHAVE gerada na criacao do registro no banco de dados");
			}
		} catch (SQLException e) {
			try {conn.rollback();} catch (Exception e1) {}
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static void update(Connection conn, long id, Estagio vo) 
	throws NotFoundException, SQLException 
	{
		PreparedStatement ps = null;
		try {
			//empresa = ?, descricao = ?, carga_horaria = ?, vagas = ?, requisito = ?, salario = ?, WHERE id = ? 
			ps = conn.prepareStatement(updatesql);
			ps.setString(1, vo.getEmpresa());
			ps.setString(2, vo.getDescricao());
			ps.setInt(3, vo.getCargaHoraria());
			ps.setInt(4, vo.getVagas());
			ps.setString(5, vo.getRequisito());
			ps.setString(6, vo.getSalario());
			ps.setLong(7, vo.getId());
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NotFoundException("Object not found [" + vo.getId() + "] .");
			}
			// SEM COMMIT
		} catch (SQLException e) {
			try {conn.rollback();} catch (Exception e1) {}
			
			throw e;
		} finally {
			closeResource(ps);
			ps = null;
		}
	}

	public static void delete(Connection conn, long id) 
	throws NotFoundException, SQLException 
	{
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(deletesql);
			ps.setLong(1, id);
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NotFoundException("Object not found [" + id + "] .");
			}
		} catch (SQLException e) {
			try {conn.rollback();} catch (Exception e1) {}
			throw e;
		} finally {
			closeResource(ps);
			ps = null;
		}
	}

	   protected static void rollbackConnection(Connection conn)
	    {
	        try
	        {
	            if (conn != null) conn.rollback();
	        }
	        catch (Exception e)
	        {
	            conn = null;
	        }
	    }
	    
	    protected static void closeResource(Statement ps, ResultSet rs)
	    {
	        try
	        {
	            if (rs != null) rs.close();
	        }
	        catch (Exception e)
	        {
	            rs = null;
	        }

	        try
	        {
	            if (ps != null) ps.close();
	        }
	        catch (Exception e)
	        {
	            ps = null;
	        }
	    }

	    protected static void closeResource(Statement ps)
	    {
	        try
	        {
	            if (ps != null) ps.close();
	        }
	        catch (Exception e)
	        {
	            ps = null;
	        }
	    }
}
