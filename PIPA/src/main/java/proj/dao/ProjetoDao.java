package proj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import proj.model.Projeto;

public class ProjetoDao extends AbstractDaoBase
{
    private final static String getsql = "SELECT * FROM Projeto  WHERE id = ?";
    private final static String listsql = "SELECT * FROM Projeto";
    private final static String listByNomeSql = "SELECT * FROM Projeto WHERE nome like %?% ";
    private final static String insertsql = "INSERT INTO Projeto (nome, responsavel, descricao, carga_horaria, vagas, requisito) VALUES( ?, ?, ?, ?, ?, ?) ";
    private final static String updatesql = "UPDATE Projeto SET nome = ?, responsavel = ?, descricao = ?, carga_horaria = ?, vagas = ?, requisito = ? WHERE id = ? ";
    private final static String deletesql = "DELETE FROM Projeto WHERE id = ?";
    private final static String getByNomeSql = "SELECT * FROM Projeto WHERE nome = ?";

    static Projeto set(ResultSet rs)
        throws SQLException
    {
        Projeto vo = new Projeto();
        vo.setId(rs.getLong("id"));
        vo.setNome(rs.getString("nome"));
        vo.setResponsavel(rs.getString("responsavel"));
        vo.setDescricao(rs.getString("descricao"));
        vo.setCargaHoraria(rs.getInt("carga_horaria"));
        vo.setVagas(rs.getInt("vagas"));
        vo.setRequisito(rs.getString("requisito"));
        return vo;
    }

    public static Projeto get(Connection conn, long id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found [" + id + "]");}
            Projeto b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static Projeto getByNome(Connection conn, String nome)
    throws NotFoundException, SQLException 
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found [" + nome + "]");}
            Projeto b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<Projeto> list(Connection conn)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<Projeto>();}
            ArrayList<Projeto> list = new ArrayList<Projeto>();
            do
            { Projeto b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<Projeto> listByNome(Connection conn, String nome)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByNomeSql);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<Projeto>();}
            ArrayList<Projeto> list = new ArrayList<Projeto>();
            do
            { Projeto b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static void insert(Connection conn, Projeto vo)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagas());
            ps.setString(6, vo.getRequisito());
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
            int id = rs.getInt(1);
            vo.setId(id);
            }else { throw new SQLException("Nao foi possivel recuperar a CHAVE gerada na criacao do registro no banco de dados");} 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static void update(Connection conn, Projeto vo)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updatesql);
            ps.setString(1, vo.getNome());
            ps.setString(2, vo.getResponsavel());
            ps.setString(3, vo.getDescricao());
            ps.setInt(4, vo.getCargaHoraria());
            ps.setInt(5, vo.getVagas());
            ps.setString(6, vo.getRequisito());
            ps.setLong(7, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ vo.getId()+"] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void delete(Connection conn, int id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(deletesql);
            ps.setInt(1,id);
            int count = ps.executeUpdate();
            if (count == 0 ){throw new NotFoundException("Object not found ["+id+"] .");}
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

}
