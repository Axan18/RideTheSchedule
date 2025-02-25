package axan18.ridetheschedule;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RideTheScheduleApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory(".").load();
        System.setProperty("spring.datasource.username", dotenv.get("DB_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DB_PASSWORD"));
        System.setProperty("spring.datasource.url", dotenv.get("DB_URL"));

        SpringApplication.run(RideTheScheduleApplication.class, args);
    }

}
