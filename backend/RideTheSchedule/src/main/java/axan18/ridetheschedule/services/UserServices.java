package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.AppUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserServices {
    Page<AppUserDTO> listUsers(int page, int size);
    Page<AppUserDTO> getUserByNameLike(String name, PageRequest pageRequest);

}
