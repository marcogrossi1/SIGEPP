package proj.dao;

import proj.model.EventoProjeto;
import proj.model.StatusEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class EventoProjetoDao {

    private final static String getsql = "SELECT * FROM EventoProjeto  WHERE id = ?";
    private final static String getByDateExpiracaoSql = "SELECT * FROM EventoProjeto  WHERE dateExpiracao = ?";
    private final static String getByDatePublicacaoSql = "SELECT * FROM EventoProjeto  WHERE datePublicacao = ?";
    private final static String listsql = "SELECT * FROM EventoProjeto";
    private final static String listByProjeto_idSql = "SELECT * FROM EventoProjeto WHERE projeto_id = ? ";
    private final static String listByMensagemSql = "SELECT * FROM EventoProjeto WHERE mensagem = ? ";
    private final static String listByStatusSql = "SELECT * FROM EventoProjeto WHERE status = ? ";
    private final static String insertsql = "INSERT INTO EventoProjeto (projeto_id, dateExpiracao, datePublicacao, mensagem, status) VALUES( ?, ?, ?, ?, ?) ";
    private final static String updatesql = "UPDATE EventoProjeto SET projeto_id = ?, dateExpiracao = ?, datePublicacao = ?, mensagem = ?, status = ? WHERE id = ? ";
    private final static String updateForProjeto_idSql = "UPDATE EventoProjeto SET projeto_id = ?  WHERE id = ? ";
    private final static String updateForDateExpiracaoSql = "UPDATE EventoProjeto SET dateExpiracao = ?  WHERE id = ? ";
    private final static String updateForDatePublicacaoSql = "UPDATE EventoProjeto SET datePublicacao = ?  WHERE id = ? ";
    private final static String updateForMensagemSql = "UPDATE EventoProjeto SET mensagem = ?  WHERE id = ? ";
    private final static String updateForStatusSql = "UPDATE EventoProjeto SET status = ?  WHERE id = ? ";
    private final static String deletesql = "DELETE FROM EventoProjeto WHERE id = ?";

    private static void closeResource(Statement ps) {
        try{if (ps != null) ps.close();}catch (Exception e){ps = null;}
    }

    private static void closeResource(Statement ps, ResultSet rs) {
        try{if (rs != null) rs.close();}catch (Exception e){rs = null;}
        try{if (ps != null) ps.close();}catch (Exception e){ps = null;}
    }

    static EventoProjeto set(ResultSet rs)
        throws SQLException
    {
        EventoProjeto vo = new EventoProjeto();
        vo.setId(rs.getLong("id"));
        vo.setProjeto_id(rs.getLong("projeto_id"));
        vo.setDateExpiracao(new Date(rs.getDate("dateExpiracao").getTime()));
        vo.setDatePublicacao(new Date(rs.getDate("dateCriacao").getTime()));
        vo.setMensagem(rs.getString("mensagem"));
		vo.setImagem(rs.getBytes("imagem"));
        vo.setStatus(StatusEvento.valueOf(rs.getString("status").toUpperCase()));
        return vo;
    }

    public static EventoProjeto get(Connection conn, long id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(getsql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found [" + id + "]");}
            EventoProjeto b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static EventoProjeto getByDateExpiracao(Connection conn, Date dateExpiracao)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
		
        try {
			// java.lang.Date -> java.sql.Date
			java.sql.Date sqlD = new java.sql.Date(dateExpiracao.getTime());

            ps = conn.prepareStatement(getByDateExpiracaoSql);
            ps.setDate(1, sqlD);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + dateExpiracao + "]");}
            EventoProjeto b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static EventoProjeto getByDatePublicacao(Connection conn, Date datePublicacao)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // java.lang.Date -> java.sql.Date
			java.sql.Date sqlD = new java.sql.Date(datePublicacao.getTime());

            ps = conn.prepareStatement(getByDatePublicacaoSql);
            ps.setDate(1, sqlD);
            rs = ps.executeQuery();
            if (!rs.next()) {throw new NotFoundException("Object not found By [" + datePublicacao + "]");}
            EventoProjeto b = set(rs);
            return b;
        }
        catch (SQLException e){throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<EventoProjeto> list(Connection conn)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listsql);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<EventoProjeto>();}
            ArrayList<EventoProjeto> list = new ArrayList<EventoProjeto>();
            do
            { EventoProjeto b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<EventoProjeto> listByProjeto_id(Connection conn, long projeto_id)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByProjeto_idSql);
            ps.setLong(1, projeto_id);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<EventoProjeto>();}
            ArrayList<EventoProjeto> list = new ArrayList<EventoProjeto>();
            do
            { EventoProjeto b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<EventoProjeto> listByMensagem(Connection conn, String mensagem)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByMensagemSql);
            ps.setString(1, mensagem);
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<EventoProjeto>();}
            ArrayList<EventoProjeto> list = new ArrayList<EventoProjeto>();
            do
            { EventoProjeto b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static ArrayList<EventoProjeto> listByStatus(Connection conn, StatusEvento status)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(listByStatusSql);
            ps.setString(1, status.toString());
            rs = ps.executeQuery();
            if (!rs.next()) {return new ArrayList<EventoProjeto>();}
            ArrayList<EventoProjeto> list = new ArrayList<EventoProjeto>();
            do
            { EventoProjeto b = set(rs); list.add(b); }
            while (rs.next());
            return list;
        }
        catch (SQLException e){ throw e;}
        finally{closeResource(ps,rs); ps = null;rs = null; }
    }

    public static void insert(Connection conn, EventoProjeto vo)
        throws SQLException
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

			java.sql.Date sqlDExpiracao = new java.sql.Date(vo.getDateExpiracao().getTime());
			java.sql.Date sqlDPublicacao = new java.sql.Date(vo.getDatePublicacao().getTime());

            ps = conn.prepareStatement(insertsql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, vo.getProjeto_id());
            ps.setDate(2, sqlDExpiracao);
            ps.setDate(3, sqlDPublicacao);
            ps.setString(4, vo.getMensagem());
            ps.setString(5, vo.getStatus().toString());
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

    public static void update(Connection conn, EventoProjeto vo)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {

			java.sql.Date sqlDExpiracao = new java.sql.Date(vo.getDateExpiracao().getTime());
			java.sql.Date sqlDPublicacao = new java.sql.Date(vo.getDatePublicacao().getTime());

            ps = conn.prepareStatement(updatesql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, vo.getProjeto_id());
            ps.setDate(2, sqlDExpiracao);
            ps.setDate(3, sqlDPublicacao);
            ps.setString(4, vo.getMensagem());
            ps.setString(5, vo.getStatus().toString());
            ps.setLong(6, vo.getId());
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ vo.getId()+"] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForProjeto_id(Connection conn, long id, long projeto_id)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForProjeto_idSql);
            ps.setLong(1, projeto_id);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForDateExpiracao(Connection conn, long id, Date dateExpiracao)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
			java.sql.Date sqlDExpiracao = new java.sql.Date(dateExpiracao.getTime());

            ps = conn.prepareStatement(updateForDateExpiracaoSql);
            ps.setDate(1, sqlDExpiracao);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForDatePublicacao(Connection conn, long id, Date datePublicacao)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            java.sql.Date sqlDPublicacao = new java.sql.Date(datePublicacao.getTime());

            ps = conn.prepareStatement(updateForDatePublicacaoSql);
            ps.setDate(1, sqlDPublicacao);
            ps.setLong(2, id);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForMensagem(Connection conn, long id, String mensagem)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForMensagemSql);
            ps.setString(1, mensagem);
            ps.setLong(2, id);
            int count = ps.executeUpdate();
            if (count == 0 ){ throw new NotFoundException("Object not found ["+ id + "] ."); }
            //SEM COMMIT 
        }
        catch (SQLException e){try{conn.rollback();} catch (Exception e1){}; throw e;}
        finally{closeResource(ps); ps = null; }
    }

    public static void updateForStatus(Connection conn, long id, StatusEvento status)
        throws NotFoundException, SQLException
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(updateForStatusSql);
            ps.setString(1, status.toString());
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

}
