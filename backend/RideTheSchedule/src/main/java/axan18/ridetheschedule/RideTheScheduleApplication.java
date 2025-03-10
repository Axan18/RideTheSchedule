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
        System.setProperty("spring.security.oauth2.client.registration.google.client-id", dotenv.get("GOOGLE_CLIENT_ID"));
        System.setProperty("spring.security.oauth2.client.registration.google.client-secret", dotenv.get("GOOGLE_CLIENT_SECRET"));
        System.setProperty("jwt.secret", dotenv.get("JWT_SECRET"));
        System.setProperty("server.ssl.key-store-password", dotenv.get("HTTPS_PSWD"));

        SpringApplication.run(RideTheScheduleApplication.class, args);
    }

}
