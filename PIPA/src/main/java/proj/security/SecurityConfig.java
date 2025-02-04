package proj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration 
@EnableWebSecurity 
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain1(HttpSecurity http) throws Exception 
    {
        http
            .authorizeHttpRequests((requests) -> 
                requests
                    .requestMatchers("/","/css/**","/img/**","/js/**")
                    .permitAll()
                    .requestMatchers("/aluno/**").hasRole("Aluno")
                    .requestMatchers("/professor/**").hasRole("Professor")
                    .requestMatchers("/empresa/**").hasRole("Empresa")
                    .requestMatchers("/administrador/**").hasRole("Administrador")
                    .requestMatchers("/pesquisa/**").hasAnyRole("Professor","Empresa")
                    .requestMatchers("/cadastro", "/cadastroAluno", "/criarAluno", "/cadastroProfessor", "/criarProfessor", "/cadastroEmpresa", "/criarEmpresa").permitAll()
                    .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) 
            .formLogin((form) -> 
                form
                    .loginPage("/login")
                    .permitAll()
            )
            .logout((logout) -> 
                logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new sha512HexPasswordEncoder();
    }
}
