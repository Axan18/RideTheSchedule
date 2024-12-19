package axan18.ridetheschedule.services;

import axan18.ridetheschedule.models.AppUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface AppUserService {
    Page<AppUserDTO> listUsers(int page, int size);
    Page<AppUserDTO> getUserByNameLike(String name, PageRequest pageRequest);

}
