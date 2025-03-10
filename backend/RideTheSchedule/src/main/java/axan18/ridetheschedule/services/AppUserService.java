package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.models.AppUserPublicDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    Page<AppUserPublicDTO> listUsersWithoutMe(UUID id, Pageable pageable);
    Page<AppUserDTO> getUserByNameLike(String name, Pageable pageable);
    Boolean deleteUser(UUID userID);
    Optional<AppUserDTO> createUser(String name, String password, String email);
    void updateLastLogin(UUID userId);
    Page<AppUserPublicDTO> getUsersByName(String username, Pageable pageable);
    String getUsernameByID(UUID id);
}
