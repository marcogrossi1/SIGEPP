package proj.dao;

import proj.model.Seguidores;
import proj.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

import proj.dao.UsuarioDao;

public class SeguidoresDao extends AbstractDaoBase{
    
    private static final String getByIdSql = "SELECT seguidor_id FROM seguidores WHERE seguido_id = ?";
    
    public static Seguidores listSeguidores(Connection conn, long id) throws SQLException{
        
        PreparedStatement ps;
        ResultSet rs;
        //usuarios usados para criar a classe Seguidores
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Usuario u;// usado para criar a lista de usuarios
        
        try{
            ps = conn.prepareStatement(getByIdSql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                long u_id = rs.getLong("seguidor_id");
            
                u = UsuarioDao.get(conn, u_id);
            
                usuarios.add(u);
            }
        }
        catch(SQLException e){
            throw e;
        }
        
        return new Seguidores(usuarios);
    }
    
}
