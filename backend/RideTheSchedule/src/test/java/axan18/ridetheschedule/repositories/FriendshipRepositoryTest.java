package axan18.ridetheschedule.repositories;

import axan18.ridetheschedule.bootstrap.BootstrapFriendshipData;
import axan18.ridetheschedule.bootstrap.BootstrapUserData;
import axan18.ridetheschedule.entities.Friendship;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapUserData.class, BootstrapFriendshipData.class})
// user n has sent request to user n+1
class FriendshipRepositoryTest {

    @Autowired
    FriendshipRepository friendshipRepository;
    @Autowired
    EntityManager entityManager;
    List<Friendship> friendships = friendshipRepository.findAll();
    @Test
    void testEstablishFriendship()
    {
        Friendship f = friendships.get(0);
        UUID senderID = f.getId().getId1();
        UUID receiverID = f.getId().getId2();

        friendshipRepository.establishFriendship(senderID,receiverID, Friendship.Status.ACCEPTED);
        entityManager.flush();
        entityManager.clear();
        friendshipRepository.flush();
        Optional<Friendship> establishedFriendship = friendshipRepository.findById(
                new Friendship.FriendshipId(senderID, receiverID));

        establishedFriendship.ifPresent(friendship -> assertNotEquals(f.getStatus(), friendship.getStatus()));
    }
}