package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import proj.model.Empresa;
import proj.model.Estagio;
import proj.model.Usuario;


@Repository
public class EmpresaDao extends AbstractDaoBase {

	private final static String listEmpresaEstagiosSql = "SELECT p.* from empresa_has_estagio ap, estagio p where ap.estagio_id = p.id and ap.empresa_id = ? ";
	private final static String getByUsuario_idSql = "SELECT * FROM Empresa  WHERE usuario_id = ?";
	private final static String getsql = "SELECT * FROM Empresa  WHERE id = ?";
	private final static String getByCnpjSql = "SELECT * FROM Empresa  WHERE cnpj = ?";
	private final static String getByEmailSql = "SELECT * FROM Empresa  WHERE email = ?";
	private final static String getByNomeSql = "SELECT * FROM Empresa WHERE nome = ?";
	private final static String listsql = "SELECT * FROM Empresa";
	private final static String listByNomeSql = "SELECT * FROM Empresa WHERE nome like %?% ";
	private final static String insertsql = "INSERT INTO Empresa (cnpj, nome, endereco, website, area, telefone, email, senha) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
	private final static String updatesql = "UPDATE Empresa SET cnpj = ?, nome = ?, endereco = ?, website = ?, area = ?, telefone = ?, email = ? WHERE id = ? ";

	static Empresa set(ResultSet rs) throws SQLException {
		Empresa vo = new Empresa();
		vo.setId(rs.getLong("id"));
		vo.setCnpj(rs.getString("cnpj"));
		vo.setNome(rs.getString("nome"));
		vo.setEndereco(rs.getString("endereco"));
		vo.setWebsite(rs.getString("website"));
		vo.setEmail(rs.getString("email"));
		vo.setArea(rs.getString("area"));
		vo.setUsuario_id(Long.parseLong(rs.getString("usuario_id")));
        vo.setTelefone(rs.getString("telefone"));
		return vo;
	}

	public static Empresa get(Connection conn, Long id) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getsql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found [" + id + "]");
			}
			Empresa b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}
	
	 public static Empresa getByUsuario_id(Connection conn, long usuario_id)
		        throws NotFoundException, SQLException
		    {
		        PreparedStatement ps = null;
		        ResultSet rs = null;
		        try {
		            ps = conn.prepareStatement(getByUsuario_idSql);
		            ps.setLong(1, usuario_id);
		            rs = ps.executeQuery();
		            if (!rs.next()) {throw new NotFoundException("Object not found By [" + usuario_id + "]");}
		            Empresa b = set(rs);
		            return b;
		        }
		        catch (SQLException e){throw e;}
		        finally{closeResource(ps,rs); ps = null;rs = null; }
		    }

	public static Empresa getByCnpj(Connection conn, String cnpj) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getByCnpjSql);
			ps.setString(1, cnpj);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found By [" + cnpj + "]");
			}
			Empresa b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static Empresa getByEmail(Connection conn, String email) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getByEmailSql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found By [" + email + "]");
			}
			Empresa b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static Empresa getByNome(Connection conn, String nome) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getByNomeSql);
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found By [" + nome + "]");
			}
			Empresa b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static ArrayList<Empresa> list(Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listsql);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Empresa>();
			}
			ArrayList<Empresa> list = new ArrayList<Empresa>();
			do {
				Empresa b = set(rs);
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

	public static ArrayList<Empresa> listByNome(Connection conn, String nome) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listByNomeSql);
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Empresa>();
			}
			ArrayList<Empresa> list = new ArrayList<Empresa>();
			do {
				Empresa b = set(rs);
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

	public static ArrayList<Estagio> listEstagiosByEmpresaId(Connection conn, long idEmpresa) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listEmpresaEstagiosSql);
			ps.setLong(1, idEmpresa);
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
	
	public static void insert(Connection conn, Empresa vo) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
            //cnpj, nome, endereco, website, area, telefone, email
			ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, vo.getCnpj());
			ps.setString(2, vo.getNome());
			ps.setString(3, vo.getEndereco());
			ps.setString(4, vo.getWebsite());
			ps.setString(5, vo.getArea());
			ps.setString(6, vo.getTelefone());
			ps.setString(7, vo.getEmail());
			ps.executeUpdate();
			conn.commit();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
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

	public static void update(Connection conn, Empresa vo) throws NotFoundException, SQLException {
	    PreparedStatement ps = null;
	    try {
	        ps = conn.prepareStatement(updatesql);
	        ps.setString(1, vo.getCnpj());
	        ps.setString(2, vo.getNome());
	        ps.setString(3, vo.getEndereco());
	        ps.setString(4, vo.getWebsite());
	        ps.setString(5, vo.getArea());
	        ps.setString(6, vo.getTelefone());
	        ps.setString(7, vo.getEmail());
	        ps.setLong(8, vo.getId());
	        int count = ps.executeUpdate();
	        if (count == 0) {
	            throw new NotFoundException("Object not found [" + vo.getId() + "] .");
	        }
	    } catch (SQLException e) {
	        try { conn.rollback(); } catch (Exception e1) {}
	        throw e;
	    } finally {
	        closeResource(ps);
	    }
	}


	/*public static void delete(Connection conn, long id) throws NotFoundException, SQLException {
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
}*/
	
	public static void delete(Connection conn, long id)
	        throws NotFoundException, SQLException
	    {
	    	Empresa e = new Empresa();
	    	Usuario u = new Usuario();
	    	String sql1 = "delete from empresa_has_estagio where empresa_id = ? ";
	    	String sql2 = "delete from empresa where id = ? ";
	    	String sql3 = "delete from seguidores where seguidor_id = ? ";
	    	String sql4 = "delete from seguidores where seguindo_id = ? ";
	    	String sql5 = "delete from usuario where id = ? ";
	    	
	    	
	    	e = EmpresaDao.get(conn, id);
	    	
	    	System.out.println(e);
	    	
	    	u = UsuarioDao.get(conn, e.getUsuario_id());


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
	    
	    public static List<Empresa> findByName(Connection conn, String nome) throws SQLException {
	        List<Empresa> empresas = new ArrayList<>();
	        String sql = "SELECT * FROM empresa WHERE nome LIKE ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, "%" + nome + "%");
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Empresa empresa = new Empresa();
	                    empresa.setId(rs.getInt("id"));
	                    empresa.setNome(rs.getString("nome"));
	                    // Preencha com outros campos necessários
	                    empresas.add(empresa);
	                }
	            }
	        }
	        return empresas;
	    }
	    
}