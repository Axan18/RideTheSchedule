package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.AppUserDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserServices {
    Page<AppUserDTO> listUsers(int page, int size);
    Optional<Page<AppUserDTO>> getUserByNameLike(String name, int page, int size);

}
