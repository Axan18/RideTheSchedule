package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.mappers.UserMapper;
import axan18.ridetheschedule.models.AppUserDTO;
import axan18.ridetheschedule.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceJPA implements UserServices {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Page<AppUserDTO> listUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest).map(userMapper::toAppUserDTO);
    }

    @Override
    public Optional<Page<AppUserDTO>> getUserByNameLike(String expression, int page, int size) {
        Page<AppUser> users = userRepository.findAllByUsernameIsLikeIgnoreCase(expression, PageRequest.of(page, size));
        if(users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.map(userMapper::toAppUserDTO));
    }
}
