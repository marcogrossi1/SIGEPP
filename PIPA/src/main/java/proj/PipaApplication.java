package proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "proj")
public class PipaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PipaApplication.class, args);
    }
}

