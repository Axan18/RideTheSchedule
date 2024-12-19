package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.mappers.AppUserMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceJPA implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    @Override
    public Page<AppUserDTO> listUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return appUserRepository.findAll(pageRequest).map(appUserMapper::toAppUserDTO);
    }

    @Override
    public Page<AppUserDTO> getUserByNameLike(String expression, PageRequest pageRequest) {
        Page<AppUser> users = appUserRepository.findAllByUsernameIsLikeIgnoreCase("%"+expression+"%", pageRequest);
        if(users.isEmpty()) {
            return Page.empty();
        }
        return users.map(appUserMapper::toAppUserDTO);
    }
}
