package proj.security;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import proj.dao.HDataSource;
import proj.dao.UsuarioDao;
import proj.model.Usuario;

@Service
public class JdbcUserDetailsService implements UserDetailsService {
	
	@Autowired
	private HDataSource ds;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
	throws UsernameNotFoundException 
	{
		
        try (Connection conn = ds.getConnection()) {
        	
        	Usuario usuario = UsuarioDao.getByNome(conn, username); //cpf

        	ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>() ;
        	roles.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRole()));
        	
        	return new User(username, usuario.getSenha(), roles);
			
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuário não encontrado com cpf informado");
		}
		
	}
	

}