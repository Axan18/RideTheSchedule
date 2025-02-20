package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface FriendshipService {
    Optional<Friendship> establishFriendship(UUID sender, UUID receiver);
    Optional<Friendship> sendFriendshipRequest(UUID sender, UUID receiver);
    Page<Friendship> getFriendshipsById(UUID id, int page, int size);
    Page<Friendship> getFriendshipsByIdAndStatus(UUID id, int page, int size);

}
