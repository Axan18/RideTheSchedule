package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.AppUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    Page<AppUserDTO> listUsers(int page, int size);
    Page<AppUserDTO> getUserByNameLike(String name, PageRequest pageRequest);
    Boolean deleteUser(UUID userID);
    Optional<AppUserDTO> createUser(String name, String password, String email);
    void updateLastLogin(UUID userId);
}
