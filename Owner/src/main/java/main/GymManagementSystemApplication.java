package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"main", "controller", "service", "repository", "model", "exception"})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "model")
public class GymManagementSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(GymManagementSystemApplication.class, args);
    }
}