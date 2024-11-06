package axan18.ridetheschedule.services;
import axan18.ridetheschedule.bootstrap.BootstrapData;
import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.mappers.UserMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceJPATest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    UserServiceJPA userServiceJPA;

    AppUser appUser;
    PageRequest pageRequest;
    final int page = 0;
    final int size = 10;
    final String name = "user";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        appUser =AppUser.builder()
                .username("User1")
                .password("test")
                .email("test@example.xyz")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build();
        pageRequest = PageRequest.of(page, size);

    }

    @Test
    void testListUsers() {
        AppUser mockUser = appUser;
        Page<AppUser> mockPage = new PageImpl<>(Collections.singletonList(mockUser));
        when(userRepository.findAll(pageRequest))
                .thenReturn(mockPage);

        AppUserDTO mockUserDTO = new AppUserDTO();
        when(userMapper.toAppUserDTO(mockUser)).thenReturn(mockUserDTO);

        Page<AppUserDTO> result = userServiceJPA.listUsers(page, size);

        assertEquals(1, result.getTotalElements(), "The result should contain one user");
        assertEquals(mockUserDTO, result.getContent().get(0), "The user should be the same as the mock user");

        verify(userRepository).findAll(pageRequest);
        verify(userMapper).toAppUserDTO(mockUser);
    }

    @Test
    void testGetEmptyUserByNameLike() {
        Page<AppUser> mockPage = new PageImpl<>(Collections.emptyList());
        when(userRepository.findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest))
                .thenReturn(mockPage);

        Page<AppUserDTO> result = userServiceJPA.getUserByNameLike(name, pageRequest);

        assertEquals(0, result.getTotalElements(), "The result should be empty");

        verify(userRepository).findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest);
    }
    @Test
    void testGetUserByNameLike() {
        AppUser mockUser = appUser;
        Page<AppUser> mockPage = new PageImpl<>(Collections.singletonList(mockUser));
        when(userRepository.findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest))
                .thenReturn(mockPage);

        AppUserDTO mockUserDTO = new AppUserDTO();
        when(userMapper.toAppUserDTO(mockUser)).thenReturn(mockUserDTO);

        Page<AppUserDTO> result = userServiceJPA.getUserByNameLike(name, pageRequest);

        assertEquals(1, result.getTotalElements(), "The result should contain one user");
        assertEquals(mockUserDTO, result.getContent().get(0), "The user should be the same as the mock user");

        verify(userRepository).findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest);
        verify(userMapper).toAppUserDTO(mockUser);
    }
}