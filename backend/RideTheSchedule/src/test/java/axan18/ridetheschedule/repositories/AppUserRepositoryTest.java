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
public class AppUserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    AppUser appUser;

    @BeforeEach
    void setUp() {
        appUser = userRepository.save(AppUser.builder()
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
        AppUser appUser1 = userRepository.save(appUser);
        userRepository.flush();
        assertThat(appUser1).isNotNull();
        assertThat(appUser1.getId()).isNotNull();
    }

    @Test
    void testSaveUserWithNull()
    {
        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(AppUser.builder()
                    .username(null)
                    .password(null)
                    .email(null)
                    .lastLoginDate(null)
                    .isActive(false)
                    .build());

            userRepository.flush();
        });
    }

    @Test
    void testOverSizeUsername() {
        assertThrows(ConstraintViolationException.class, () -> {
            appUser.setUsername("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pellentesque, mauris ut lacinia cursus");
            userRepository.save(appUser);
            userRepository.flush();
        });
    }
    @Test
    void testFindAllByUsernameIsLike() {
        userRepository.save(appUser);
        userRepository.save(AppUser.builder()
                .username("User2")
                .password("test")
                .email("example")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build());
        Page<AppUser> appUserPage = userRepository.findAllByUsernameIsLikeIgnoreCase("User%", null);
        assertThat(appUserPage.getContent().size()).isEqualTo(2);
    }
    @Test
    void testEmailUnique() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(appUser);
            userRepository.save(AppUser.builder()
                    .username("User2")
                    .password("test")
                    .email("test@example.xyz") // duplicate email
                    .lastLoginDate(Date.valueOf("2021-01-01"))
                    .isActive(true)
                    .build());
            userRepository.flush();
        });
    }
    @Test
    void testEmailNull() {
        assertThrows(ConstraintViolationException.class, () -> {
            appUser.setEmail(null);
            userRepository.save(appUser);
            userRepository.flush();
        });
    }
    @Test
    void testFindUserById() {
        AppUser savedUser = userRepository.save(appUser);
        Optional<AppUser> appUser1 = userRepository.findById(savedUser.getId());
        assertThat(appUser1).isPresent();
        assertThat(appUser1.get().getId()).isEqualTo(savedUser.getId());
    }
    @Test
    void testFindUserByIdNotFound() {
        Optional<AppUser> appUser1 = userRepository.findById(UUID.randomUUID());
        assertThat(appUser1).isEmpty();
    }
    @Test
    void testDeleteUser() {
        AppUser savedUser = userRepository.save(appUser);
        userRepository.delete(savedUser);
        userRepository.flush();
        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }
}