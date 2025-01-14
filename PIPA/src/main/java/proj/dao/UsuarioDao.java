package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.*;


import proj.model.Usuario;

public class UsuarioDao {
	/* EM TESTES / DESATIVADO
	 * 
	 * 
	private Connection connection;

    public UsuarioDao(Connection connection) {
        this.connection = connection;
    }*/

    private final static String getsql = "SELECT * FROM usuario  WHERE id = ?";
    private final static String getByNomeSql = "SELECT * FROM usuario  WHERE nome = ?";
    private final static String listsql = "SELECT * FROM usuario";
    private final static String insertsql = "INSERT INTO usuario (nome, senha, role) VALUES( ?, ?, ?) ";
    private final static String updatesql = "UPDATE usuario SET nome = ?, senha = ?, role = ? WHERE id = ? ";
    private final static String updateForNomeSql = "UPDATE usuario SET nome = ?  WHERE id = ? ";
    private final static String updateForSenhaSql = "UPDATE usuario SET senha = ?  WHERE id = ? ";
    private final static String updateForRoleSql = "UPDATE usuario SET role = ?  WHERE id = ? ";
    private final static String deletesql = "DELETE FROM usuario WHERE id = ?";

    private static void closeResource(Statement ps) {
        try{if (ps != null) ps.close();}catch (Exception e){ps = null;}
    }

    private static void closeResource(Statement ps, ResultSet rs) {
        try{if (rs != null) rs.close();}catch (Exception e){rs = null;}
        try{if (ps != null) ps.close();}catch (Exception e){ps = null;}
    }
    
    static Usuario set(ResultSet rs)
        throws SQLException
    {
        Usuario vo = new Usuario();
        vo.setId(rs.getLong("id"));
        vo.setNome(rs.getString("nome"));
        vo.setSenha(rs.getString("senha"));
        vo.setRole(rs.getString("role"));
        return vo;
    }

    public static Usuario get(Connection conn, long id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found [" + id + "]");}
            Usuario b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static Usuario getByNome(Connection conn, String nome)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + nome + "]");}
            Usuario b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<Usuario> list(Connection conn)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<Usuario>();}
            ArrayList<Usuario> list = new ArrayList<Usuario>();
            do
            { Usuario b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static void insert(Connection conn, Usuario vo)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getSenha());
            ps.setString(3, vo.getRole());
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

    public static void update(Connection conn, Usuario vo)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getSenha());
            ps.setString(3, vo.getRole());
            ps.setLong(4, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ vo.getId()+"] ."); }
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

    public static void updateForSenha(Connection conn, long id, String senha)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForSenhaSql);
            ps.setString(1, senha);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForRole(Connection conn, long id, String role)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForRoleSql);
            ps.setString(1, role);
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
    
    /* EM TESTES / DESATIVADO
     *
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setDescricao(rs.getString("descricao"));
                usuario.setBannerUrl(rs.getString("banner_url"));
                usuario.setFotoPerfilUrl(rs.getString("foto_perfil_url"));
                return usuario;
            }
        }
        return null;
    }
    
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, descricao = ?, banner_url = ?, foto_perfil_url = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getDescricao());
            stmt.setString(3, usuario.getBannerUrl());
            stmt.setString(4, usuario.getFotoPerfilUrl());
            stmt.setLong(5, usuario.getId());
            stmt.executeUpdate();
        }
    }*/
}
