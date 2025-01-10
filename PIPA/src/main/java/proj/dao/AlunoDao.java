package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import proj.model.Aluno;
import proj.model.Estagio;
import proj.model.Projeto;

public class AlunoDao extends AbstractDaoBase {

	private final static String listAlunoProjetosSql = "select p.* from aluno_has_projeto ap, projeto p  where ap.projeto_id = p.id and ap.aluno_id = ? ";
	private final static String listAlunoEstagiosSql = "select p.* from aluno_has_estagio ap, estagio p  where ap.estagio_id = p.id and ap.aluno_id = ? ";

	private final static String getsql = "SELECT * FROM Aluno  WHERE id = ?";
	private final static String getByCpfSql = "SELECT * FROM Aluno  WHERE cpf = ?";
	private final static String getByEmailSql = "SELECT * FROM Aluno  WHERE email = ?";
	private final static String listsql = "SELECT * FROM Aluno";
	private final static String listByNomeSql = "SELECT * FROM Aluno WHERE nome like %?% ";
	private final static String insertsql = "INSERT INTO Aluno (cpf, nome, curso, campus, email, senha, periodo) VALUES( ?, ?, ?, ?, ?, ?, ?) ";
	private final static String updatesql = "UPDATE Aluno SET cpf = ?, nome = ?, curso = ?, campus = ?, email = ?, senha = ?, periodo = ? WHERE id = ? ";
	private final static String deletesql = "DELETE FROM Aluno WHERE id = ?";

	static Aluno set(ResultSet rs) throws SQLException {
		Aluno vo = new Aluno();
		vo.setId(rs.getLong("id"));
		vo.setCpf(rs.getString("cpf"));
		vo.setNome(rs.getString("nome"));
		vo.setCurso(rs.getString("curso"));
		vo.setCampus(rs.getString("campus"));
		vo.setEmail(rs.getString("email"));
		vo.setSenha(rs.getString("senha"));
		vo.setPeriodo(rs.getString("periodo"));
		return vo;
	}

	public static Aluno get(Connection conn, long id) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getsql);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found [" + id + "]");
			}
			Aluno b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static Aluno getByCfp(Connection conn, String cfp) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getByCpfSql);
			ps.setString(1, cfp);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found By [" + cfp + "]");
			}
			Aluno b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static Aluno getByEmail(Connection conn, String email) throws NotFoundException, SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(getByEmailSql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Object not found By [" + email + "]");
			}
			Aluno b = set(rs);
			return b;
		} catch (SQLException e) {
			throw e;
		} finally {
			closeResource(ps, rs);
			ps = null;
			rs = null;
		}
	}

	public static ArrayList<Aluno> list(Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listsql);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Aluno>();
			}
			ArrayList<Aluno> list = new ArrayList<Aluno>();
			do {
				Aluno b = set(rs);
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

	public static ArrayList<Aluno> listByNome(Connection conn, String nome) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listByNomeSql);
			ps.setString(1, nome);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return new ArrayList<Aluno>();
			}
			ArrayList<Aluno> list = new ArrayList<Aluno>();
			do {
				Aluno b = set(rs);
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

	public static ArrayList<Projeto> listProjetosByAlunoId(Connection conn, long idAluno) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listAlunoProjetosSql);
			ps.setLong(1, idAluno);
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
	
	
	public static ArrayList<Estagio> listEstagiosByAlunoId(Connection conn, long idAluno) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(listAlunoEstagiosSql);
			ps.setLong(1, idAluno);
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
	
	
	public static void insert(Connection conn, Aluno vo) 
	throws SQLException 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, vo.getCpf());
			ps.setString(2, vo.getNome());
			ps.setString(3, vo.getCurso());
			ps.setString(4, vo.getCampus());
			ps.setString(5, vo.getEmail());
			ps.setString(6, vo.getSenha());
			ps.setString(7, vo.getPeriodo());
			ps.executeUpdate();
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

	public static void update(Connection conn, Aluno vo) 
	throws NotFoundException, SQLException 
	{
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(updatesql);
			ps.setString(1, vo.getCpf());
			ps.setString(2, vo.getNome());
			ps.setString(3, vo.getCurso());
			ps.setString(4, vo.getCampus());
			ps.setString(5, vo.getEmail());
			ps.setString(6, vo.getSenha());
			ps.setString(7, vo.getPeriodo());
			ps.setLong(8, vo.getId());
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

	public static void delete(Connection conn, int id) 
	throws NotFoundException, SQLException 
	{
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(deletesql);
			ps.setInt(1, id);
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

}
