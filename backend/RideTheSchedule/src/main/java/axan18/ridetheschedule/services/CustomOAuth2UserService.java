package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        Optional<AppUser> existingUser = appUserRepository.findByEmail(email);
        if(existingUser.isEmpty()){
            AppUser newUser = AppUser.builder()
                    .id(UUID.randomUUID())
                    .email(email)
                    .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                    .isActive(true)
                    .lastLoginDate(Date.valueOf(LocalDate.now()))
                    .username(oAuth2User.getName())
                    .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            appUserRepository.save(newUser);
        }
        if (existingUser.isPresent()) {
            AppUser user = existingUser.get();
            user.setLastLoginDate(Date.valueOf(LocalDate.now()));
            user.setLastModified(Timestamp.valueOf(LocalDateTime.now()));
            appUserRepository.save(user);
        }

        return oAuth2User;
    }
}
