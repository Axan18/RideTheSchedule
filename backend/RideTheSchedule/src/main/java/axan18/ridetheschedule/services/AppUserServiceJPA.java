package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.mappers.AppUserMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceJPA implements AppUserServices {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;
    @Override
    public Page<AppUserDTO> listUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest).map(appUserMapper::toAppUserDTO);
    }

    @Override
    public Page<AppUserDTO> getUserByNameLike(String expression, PageRequest pageRequest) {
        Page<AppUser> users = userRepository.findAllByUsernameIsLikeIgnoreCase("%"+expression+"%", pageRequest);
        if(users.isEmpty()) {
            return Page.empty();
        }
        return users.map(appUserMapper::toAppUserDTO);
    }
}
