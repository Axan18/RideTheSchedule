package axan18.ridetheschedule.bootstrap;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Order(1)
public class BootstrapUserData implements CommandLineRunner {
    private final AppUserRepository appUserRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        List<AppUser> userList = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            AppUser user = AppUser.builder()
                    .id(UUID.randomUUID())
                    .username("user" + i)
                    .password("password" + i)
                    .email("user" + i + "@example.com")
                    .createdAt(Timestamp.valueOf(LocalDateTime.now().minusDays(i * 2)))
                    .lastModified(Timestamp.valueOf(LocalDateTime.now().minusDays(i)))
                    .lastLoginDate(Date.valueOf(LocalDate.now().minusDays(i)))
                    .isActive(i % 2 == 0)
                    .build();

            userList.add(user);
        }
        appUserRepository.saveAll(userList);
        System.out.println("Users loaded: " + userList.size());
    }

}
