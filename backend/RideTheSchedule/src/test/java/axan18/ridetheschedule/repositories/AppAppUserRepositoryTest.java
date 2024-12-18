package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.AppUser;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
//@Import(BootstrapUserData.class)
public class AppAppUserRepositoryTest {

    @Autowired
    AppUserRepository appUserRepository;
    AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = appUserRepository.save(AppUser.builder()
                .username("User1")
                .password("test")
                .email("test@example.xyz")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build());
    }
    @Test
    void testSaveUser()
    {
        AppUser appUser1 = appUserRepository.save(appUser);
        appUserRepository.flush();
        assertThat(appUser1).isNotNull();
        assertThat(appUser1.getId()).isNotNull();
    }

    @Test
    void testSaveUserWithNull()
    {
        assertThrows(ConstraintViolationException.class, () -> {
            appUserRepository.save(AppUser.builder()
                    .username(null)
                    .password(null)
                    .email(null)
                    .lastLoginDate(null)
                    .isActive(false)
                    .build());

            appUserRepository.flush();
        });
    }

    @Test
    void testOverSizeUsername() {
        assertThrows(ConstraintViolationException.class, () -> {
            appUser.setUsername("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pellentesque, mauris ut lacinia cursus");
            appUserRepository.save(appUser);
            appUserRepository.flush();
        });
    }
    @Test
    void testFindAllByUsernameIsLike() {
        appUserRepository.save(appUser);
        appUserRepository.save(AppUser.builder()
                .username("User2")
                .password("test")
                .email("example")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build());
        Page<AppUser> appUserPage = appUserRepository.findAllByUsernameIsLikeIgnoreCase("User%", null);
        assertThat(appUserPage.getContent().size()).isEqualTo(2);
    }
    @Test
    void testEmailUnique() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            appUserRepository.save(appUser);
            appUserRepository.save(AppUser.builder()
                    .username("User2")
                    .password("test")
                    .email("test@example.xyz") // duplicate email
                    .lastLoginDate(Date.valueOf("2021-01-01"))
                    .isActive(true)
                    .build());
            appUserRepository.flush();
        });
    }
    @Test
    void testEmailNull() {
        assertThrows(ConstraintViolationException.class, () -> {
            appUser.setEmail(null);
            appUserRepository.save(appUser);
            appUserRepository.flush();
        });
    }
    @Test
    void testFindUserById() {
        AppUser savedUser = appUserRepository.save(appUser);
        Optional<AppUser> appUser1 = appUserRepository.findById(savedUser.getId());
        assertThat(appUser1).isPresent();
        assertThat(appUser1.get().getId()).isEqualTo(savedUser.getId());
    }
    @Test
    void testFindUserByIdNotFound() {
        Optional<AppUser> appUser1 = appUserRepository.findById(UUID.randomUUID());
        assertThat(appUser1).isEmpty();
    }
    @Test
    void testDeleteUser() {
        AppUser savedUser = appUserRepository.save(appUser);
        appUserRepository.delete(savedUser);
        appUserRepository.flush();
        assertThat(appUserRepository.findById(savedUser.getId())).isEmpty();
    }
}