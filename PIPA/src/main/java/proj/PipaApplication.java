package proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication(scanBasePackages = "proj")
@EntityScan(basePackages = "proj.model")
public class PipaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PipaApplication.class, args);
    }
}

