package proj.security;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import proj.dao.AlunoDao;
import proj.dao.HDataSource;
import proj.model.Aluno;

@Service
public class JdbcUserDetailsService implements UserDetailsService {
	
	@Autowired
	private HDataSource ds;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
	throws UsernameNotFoundException 
	{
        try (Connection conn = ds.getConnection()) {
        	
        	Aluno aluno = AlunoDao.getByCfp(conn, username); //cpf

        	return new User(username, aluno.getSenha(), new ArrayList<GrantedAuthority>() );
			
		} catch (Exception e) {
			throw new UsernameNotFoundException("Usuário não encontrado com cpf informado");
		}
		
	}
	

}