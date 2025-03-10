package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.mappers.AppUserMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.models.AppUserPublicDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceJPA implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    @Override
    public Page<AppUserPublicDTO> listUsersWithoutMe(UUID id, Pageable pageable) {
        return appUserRepository.findAllWithoutMe(id,pageable).map(appUserMapper::toAppUserPublicDTO);
    }

    @Override
    public Page<AppUserDTO> getUserByNameLike(String expression, Pageable pageable) {
        Page<AppUser> users = appUserRepository.findAllByUsernameIsLikeIgnoreCase("%"+expression+"%", pageable);
        if(users.isEmpty()) {
            return Page.empty();
        }
        return users.map(appUserMapper::toAppUserDTO);
    }

    @Override
    public Boolean deleteUser(UUID userID) {
        try{
            appUserRepository.deleteById(userID);
            return true;
        }catch (EmptyResultDataAccessException e) {
            System.err.println("User with ID " + userID + " does not exist.");
        } catch (IllegalArgumentException e) {
            System.err.println("Provided userID is null.");
        } catch (DataAccessException e) {
            System.err.println("Database error occurred while deleting user: " + e.getMessage());
        }
        return false;

    }

    @Override
    public Optional<AppUserDTO> createUser(String name, String password, String email) {
        Optional<AppUser> existingUser = appUserRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        AppUser user = AppUser.builder()
                .username(name)
                .password(password)
                .email(email)
                .lastLoginDate(Date.valueOf(LocalDate.now()))
                .isActive(true)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        try {
            AppUser createdUser = appUserRepository.save(user);
            return Optional.of(appUserMapper.toAppUserDTO(createdUser));
        }catch (Exception e){
            System.err.println("Creating user failed");
        }
        return Optional.empty();
    }

    @Override
    public void updateLastLogin(UUID userId) {
        appUserRepository.updateLastLogin(userId, Date.valueOf(LocalDate.now()));
    }

    @Override
    public Page<AppUserPublicDTO> getUsersByName(String username, Pageable pageable) {
        Page<AppUser> users = appUserRepository.searchByUsername(username, pageable);
        if(users.isEmpty()){
            return Page.empty();
        }
        return users.map(appUserMapper::toAppUserPublicDTO);
    }

    @Override
    public String getUsernameByID(UUID id) {
        return appUserRepository.getUsernameByID(id);
    }

}
