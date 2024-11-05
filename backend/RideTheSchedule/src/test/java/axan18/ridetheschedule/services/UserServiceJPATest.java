package axan18.ridetheschedule.services;
import axan18.ridetheschedule.bootstrap.BootstrapData;
import axan18.ridetheschedule.models.AppUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@SpringBootTest
class UserServiceJPATest {

    @Autowired
    UserServiceJPA userServiceJPA;

    @Test
    void testListUsers() {
        Page<AppUserDTO> users = userServiceJPA.listUsers(0, 10);
        assertNotNull(users, "The users page should not be null");
        assertEquals(10, users.getSize(), "The size of the users page should be 10");
        assertTrue(users.getTotalElements() >= 10, "There should be at least 10 users in total");
    }

    @Test
    void testGetEmptyUserByNameLike() {
        String name = "user";
        assertTrue(userServiceJPA.getUserByNameLike(name, 0, 10).isEmpty());
    }
    @Test
    void testGetUserByNameLike() {
        String name = "user";
        userServiceJPA.getUserByNameLike(name+'%' , 0, 10).ifPresent(users -> {
            assertFalse(users.isEmpty(), "The users list should not be empty");
            assertTrue(users.stream().allMatch(user -> user.getUsername().contains(name)), "All users should contain the name");
        });
    }

}