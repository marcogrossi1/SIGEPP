package proj.dao;

import proj.model.Seguidores;
import proj.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class SeguidoresDao extends AbstractDaoBase{
    
    private static final String getSeguidoresByIdSql = "SELECT seguidor_id FROM Seguidores WHERE seguindo_id = ?";

    private static final String getSeguidosIdSql = "SELECT seguindo_id FROM Seguidores WHERE seguidor_id = ?";

    private static final String getRelationById = "SELECT * FROM Seguidores WHERE seguidor_id = ? AND seguindo_id = ?";

    private static final String insertRelationById = "INSERT INTO Seguidores (seguindo_id, seguidor_id) VALUES (?, ?)";

    private static final String deleteRelationById = "DELETE FROM `Seguidores` WHERE seguidor_id = ? AND seguindo_id = ?";
    
    public static Seguidores listSeguidores(Connection conn, long id) throws SQLException{
        
        PreparedStatement ps = null;
        ResultSet rs =null;
        //usuarios usados para criar a classe Seguidores
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario u;// usado para criar a lista de usuarios
        
        try{
            ps = conn.prepareStatement(getSeguidoresByIdSql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) return new Seguidores();//retorna wrapper vazio
            
            do {
                long u_id = rs.getLong("seguidor_id");
            
                u = UsuarioDao.get(conn, u_id);
            
                usuarios.add(u);

            } while (rs.next());
        }
        //"""Gracefully""" shutdown
        catch (SQLException e) { throw e;}
        finally{
            closeResource(ps,rs); 
            ps = null;rs = null; }
        
        return new Seguidores(usuarios);
    }

    public static Seguidores listSeguidos(Connection conn, long id) throws SQLException{
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        //usuarios usados para criar a classe Seguidores
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario u;// usado para criar a lista de usuarios
        
        try {
            ps = conn.prepareStatement(getSeguidosIdSql);
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) return new Seguidores();
            
            do {
                long u_id = rs.getLong("seguindo_id");
            
                u = UsuarioDao.get(conn, u_id);
            
                usuarios.add(u);    
                
            } while (rs.next());
        
            return new Seguidores(usuarios);

        }
        catch (SQLException e) { throw e;}
        finally{
            closeResource(ps,rs); 
            ps = null;rs = null; }
    }

    private static Seguidores getSeguido(Connection conn, long seguidor_id, long seguindo_id)
    throws SQLException{

        PreparedStatement ps;
        ResultSet rs;
        Usuario uSeguido = null;
       
        ps = conn.prepareStatement(getRelationById);
        ps.setLong(1, seguidor_id);
        ps.setLong(2, seguindo_id);
        rs = ps.executeQuery();
        
        if (rs.next()) 
            uSeguido = UsuarioDao.get(conn, rs.getLong("seguindo_id"));//usuario seguido
        

        Seguidores newSeguido = new Seguidores();

        newSeguido.appendSeguidor(uSeguido);
    
        return newSeguido;
    }

    //verifica se o usuário 'seguidor_id' está seguindo 'seguindo_id'
    public static boolean isFollowing(Connection conn, long seguidor_id, long seguindo_id)
    throws SQLException{
        
        PreparedStatement ps = null;
        ResultSet rs = null;
       
        try{
            ps = conn.prepareStatement(getRelationById);
            ps.setLong(1, seguidor_id);
            ps.setLong(2, seguindo_id);
            rs = ps.executeQuery();
            
            return rs.next();

        }
        catch (SQLException e){
            throw new NotFoundException("Object not found [" + seguidor_id + " or "+ seguindo_id + "] .");

        }

        finally{
            closeResource(ps,rs);
             ps = null;rs = null; 

        }        
    }
    
    //insere uma nova relação (retorna o usuario seguido)
    public static Seguidores insertFollowRelation(Connection conn, long seguidor_id, long seguindo_id) throws SQLException, NotFoundException{

        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps = conn.prepareStatement(insertRelationById, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, seguindo_id);
            ps.setLong(2, seguidor_id);
            int count = ps.executeUpdate();

            conn.commit();

            if(count != 0)
                return getSeguido(conn, seguidor_id, seguindo_id);
            else
                throw new NotFoundException("Object not found [" + seguidor_id + " or "+ seguindo_id + "] .");
        }
        catch (SQLException e){
            try{conn.rollback();
            } 
            catch (Exception e1){}; 
            throw e;}

        finally{closeResource(ps,rs); ps = null;rs = null; 
        }
        
    }

    public static void deleteFollowRelation(Connection conn, long seguidor_id, long seguindo_id) throws SQLException, NotFoundException{

        PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(deleteRelationById);
			ps.setLong(1, seguidor_id);
			ps.setLong(2, seguindo_id);
			int count = ps.executeUpdate();

            conn.commit();

			if (count == 0) {
				throw new NotFoundException("Object not found [" + seguidor_id + " or "+ seguindo_id + "] .");
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
