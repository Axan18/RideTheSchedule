package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.mappers.AppUserMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.sql.Date;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AppUserServiceJPATest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AppUserMapper appUserMapper;

    @InjectMocks
    AppUserServiceJPA userServiceJPA;

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
        when(appUserRepository.findAll(pageRequest))
                .thenReturn(mockPage);

        AppUserDTO mockUserDTO = new AppUserDTO();
        when(appUserMapper.toAppUserDTO(mockUser)).thenReturn(mockUserDTO);

        Page<AppUserDTO> result = userServiceJPA.listUsers(page, size);

        assertEquals(1, result.getTotalElements(), "The result should contain one user");
        assertEquals(mockUserDTO, result.getContent().get(0), "The user should be the same as the mock user");

        verify(appUserRepository).findAll(pageRequest);
        verify(appUserMapper).toAppUserDTO(mockUser);
    }

    @Test
    void testGetEmptyUserByNameLike() {
        Page<AppUser> mockPage = new PageImpl<>(Collections.emptyList());
        when(appUserRepository.findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest))
                .thenReturn(mockPage);

        Page<AppUserDTO> result = userServiceJPA.getUserByNameLike(name, pageRequest);

        assertEquals(0, result.getTotalElements(), "The result should be empty");

        verify(appUserRepository).findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest);
    }
    @Test
    void testGetUserByNameLike() {
        AppUser mockUser = appUser;
        Page<AppUser> mockPage = new PageImpl<>(Collections.singletonList(mockUser));
        when(appUserRepository.findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest))
                .thenReturn(mockPage);

        AppUserDTO mockUserDTO = new AppUserDTO();
        when(appUserMapper.toAppUserDTO(mockUser)).thenReturn(mockUserDTO);

        Page<AppUserDTO> result = userServiceJPA.getUserByNameLike(name, pageRequest);

        assertEquals(1, result.getTotalElements(), "The result should contain one user");
        assertEquals(mockUserDTO, result.getContent().get(0), "The user should be the same as the mock user");

        verify(appUserRepository).findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest);
        verify(appUserMapper).toAppUserDTO(mockUser);
    }
}