package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.entities.AppUser;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
//@Import(BootstrapUserData.class)
public class AppUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void testSaveUser()
    {
        AppUser appUser = userRepository.save(AppUser.builder()
                .username("test")
                .password("test")
                .email("testmail@xyz.com")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build());
        userRepository.flush();
        assertThat(appUser).isNotNull();
        assertThat(appUser.getId()).isNotNull();
    }

    @Test
    void testSaveUserWithNull()
    {
        assertThrows(ConstraintViolationException.class, () -> {
            AppUser appUser = userRepository.save(AppUser.builder()
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
            AppUser appUser = userRepository.save(AppUser.builder()
                    .username("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pellentesque, mauris ut lacinia cursus, lectus mauris malesuada ante, nec efficitur urna arcu vitae eros. Aenean lacinia diam lacus, in sagittis ex aliquam et. Proin eu hendrerit lectus, ac sodales orci. Cras mollis porta arcu, ac tincidunt nibh bibendum id.")
                    .password("test")
                    .email("dfds    @xyz.com")
                    .lastLoginDate(Date.valueOf("2021-01-01"))
                    .isActive(true)
                    .build());
            userRepository.flush();
        });
    }
    @Test
    void testFindAllByUsernameIsLike() {
        AppUser appUser = userRepository.save(AppUser.builder()
                .username("User1")
                .password("test")
                .email("test")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build());
        AppUser appUser2 = userRepository.save(AppUser.builder()
                .username("User2")
                .password("test")
                .email("test2")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build());
        Page<AppUser> appUserPage = userRepository.findAllByUsernameIsLikeIgnoreCase("User%", null);
        assertThat(appUserPage.getContent().size()).isEqualTo(2);
    }
    @Test
    void testEmailUnique() {
        assertThrows(DataIntegrityViolationException.class, () -> {
            AppUser appUser = userRepository.save(AppUser.builder()
                    .username("User1")
                    .password("test")
                    .email("test")
                    .lastLoginDate(Date.valueOf("2021-01-01"))
                    .isActive(true)
                    .build());
            AppUser appUser2 = userRepository.save(AppUser.builder()
                    .username("User2")
                    .password("test")
                    .email("test")
                    .lastLoginDate(Date.valueOf("2021-01-01"))
                    .isActive(true)
                    .build());
            userRepository.flush();
        });
    }
    @Test
    void testEmailNull() {
        assertThrows(ConstraintViolationException.class, () -> {
            AppUser appUser = userRepository.save(AppUser.builder()
                    .username("User1")
                    .password("test")
                    .email(null)
                    .lastLoginDate(Date.valueOf("2021-01-01"))
                    .isActive(true)
                    .build());
            userRepository.flush();
        });
    }
}
