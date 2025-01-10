package proj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //nao eh necessario pq a anotacao @EnableWebSecurity deriva de @Configuration
@EnableWebSecurity //permite que nossa configuracao substitua as configurações default de seguranca dos Starters do Spring Security - https://stackoverflow.com/questions/44671457/what-is-the-use-of-enablewebsecurity-in-spring
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new sha512HexPasswordEncoder();
	}

}