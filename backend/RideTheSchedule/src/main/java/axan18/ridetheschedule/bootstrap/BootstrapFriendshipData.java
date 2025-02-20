package axan18.ridetheschedule.bootstrap;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class BootstrapFriendshipData implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final FriendshipRepository friendshipRepository;

    @Override
    public void run(String... args) throws Exception {
        List<AppUser> users = appUserRepository.findAll();
        for(int i = 0; i<users.size()-1;i++)
        {
            friendshipRepository.save(
              Friendship.builder()
                      .id(new Friendship.FriendshipId(users.get(i).getId(),users.get(i+1).getId()))
                      .user1(users.get(i))
                      .user2(users.get(i+1))
                      .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                      .status(Friendship.Status.PENDING)
                      .build()
            );
        }
    }
}
