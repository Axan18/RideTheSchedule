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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AppUserServiceJPATest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private AppUserMapper appUserMapper;

    @InjectMocks
    AppUserServiceJPA appUserServiceJPA;

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

//    @Test
//    void testListUsers() {
//        AppUser mockUser = appUser;
//        Page<AppUser> mockPage = new PageImpl<>(Collections.singletonList(mockUser));
//        when(appUserRepository.findAll(pageRequest))
//                .thenReturn(mockPage);
//
//        AppUserDTO mockUserDTO = new AppUserDTO();
//        when(appUserMapper.toAppUserDTO(mockUser)).thenReturn(mockUserDTO);
//
//        Page<AppUserDTO> result = appUserServiceJPA.listUsers(page, size);
//
//        assertEquals(1, result.getTotalElements(), "The result should contain one user");
//        assertEquals(mockUserDTO, result.getContent().get(0), "The user should be the same as the mock user");
//
//        verify(appUserRepository).findAll(pageRequest);
//        verify(appUserMapper).toAppUserDTO(mockUser);
//    }

    @Test
    void testGetEmptyUserByNameLike() {
        Page<AppUser> mockPage = new PageImpl<>(Collections.emptyList());
        when(appUserRepository.findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest))
                .thenReturn(mockPage);

        Page<AppUserDTO> result = appUserServiceJPA.getUserByNameLike(name, pageRequest);

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

        Page<AppUserDTO> result = appUserServiceJPA.getUserByNameLike(name, pageRequest);

        assertEquals(1, result.getTotalElements(), "The result should contain one user");
        assertEquals(mockUserDTO, result.getContent().get(0), "The user should be the same as the mock user");

        verify(appUserRepository).findAllByUsernameIsLikeIgnoreCase("%" + name + "%", pageRequest);
        verify(appUserMapper).toAppUserDTO(mockUser);
    }
    @Test
    void testCreateUser()
    {
        String name ="user";
        String psswd = "psswd";
        String email = "mail@google.com";
        AppUser user = AppUser.builder()
                .username(name)
                .password(psswd)
                .email(email)
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .isActive(true)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        AppUserDTO userDTO = AppUserDTO.builder()
                .username(name)
                .password(psswd)
                .email(email)
                .build();
        when(appUserRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(appUserRepository.save(any(AppUser.class))).thenReturn(user);
        when(appUserMapper.toAppUserDTO(user)).thenReturn(userDTO);

        Optional<AppUserDTO> createdUser = appUserServiceJPA.createUser(name,psswd,email);
        assertTrue(createdUser.isPresent());
        assertEquals(userDTO,createdUser.get());
        verify(appUserRepository).save(any(AppUser.class));
    }
    @Test
    void testDeleteUser()
    {
        AppUser user = appUser;
        user.setId(UUID.randomUUID());
        when(appUserRepository.save(user)).thenReturn(user);
        doNothing().when(appUserRepository).deleteById(user.getId());
        Boolean result = appUserServiceJPA.deleteUser(user.getId());
        assertTrue(result);
        verify(appUserRepository).deleteById(user.getId());
    }
    @Test
    void testUpdateLastLoginDate()
    {
        AppUser user = appUser;
        UUID id = UUID.randomUUID();
        user.setId(id);
        LocalDate now = LocalDate.now();
        doNothing().when(appUserRepository).updateLastLogin(user.getId(), Date.valueOf(now));
        appUserServiceJPA.updateLastLogin(id);
        verify(appUserRepository).updateLastLogin(id,Date.valueOf(now));
    }
}