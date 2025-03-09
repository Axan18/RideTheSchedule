package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.models.FriendshipDTO;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendshipService {
    Optional<Friendship> establishFriendship(UUID sender, UUID receiver);
    Optional<Friendship> sendFriendshipRequest(UUID sender, UUID receiver);
    List<FriendshipDTO> getFriendshipsById(UUID id);
    Page<Friendship> getFriendshipsByIdAndStatus(UUID id, Friendship.Status status, int page, int size);

}
