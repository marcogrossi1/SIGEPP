package proj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfig {

    // Configura o DataSource
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/pipa2?createDatabaseIfNotExist=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        return dataSource;
    }

    // Cria um bean para o objeto Connection
    @Bean
    public java.sql.Connection connection(DataSource dataSource) throws java.sql.SQLException {
        return dataSource.getConnection();
    }
}
