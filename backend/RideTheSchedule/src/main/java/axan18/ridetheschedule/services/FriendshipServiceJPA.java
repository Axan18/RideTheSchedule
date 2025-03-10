package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.mappers.FriendshipMapper;
import axan18.ridetheschedule.models.FriendshipDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendshipServiceJPA implements FriendshipService {

    private final AppUserRepository appUserRepository;
    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @Override
    public Optional<Friendship> establishFriendship(UUID sender, UUID receiver) {
        friendshipModifyingValidation(sender,receiver);
        Optional<Friendship> existingFriendship = friendshipRepository.getFriendshipBetween(sender,receiver);
        if (existingFriendship.isEmpty()) {
            throw new IllegalStateException("Pending request doesn't exist");
        }
        friendshipRepository.establishFriendship(sender,receiver, Friendship.Status.ACCEPTED);
        return friendshipRepository.getFriendshipBetween(sender,receiver);
    }

    @Override
    public Optional<Friendship> sendFriendshipRequest(UUID sender, UUID receiver) {
        friendshipModifyingValidation(sender,receiver);
        Optional<Friendship> existingFriendship = friendshipRepository.getFriendshipBetween(sender,receiver);
        if (existingFriendship.isPresent()) {
            throw new IllegalStateException("Friendship status: "+existingFriendship.get().getStatus());
        }

        return Optional.of(friendshipRepository.save(Friendship.builder()
                .lastModified(Timestamp.valueOf(LocalDateTime.now()))
                .status(Friendship.Status.PENDING)
                .id(new Friendship.FriendshipId(sender,receiver))
                .user1(appUserRepository.findById(sender).get())
                .user2(appUserRepository.findById(receiver).get())
                .build()));
    }

    @Override
    public List<FriendshipDTO> getFriendshipsById(UUID id) {
        return friendshipRepository.getFriendshipsById(id).stream().map(friendshipMapper::toFriendshipDTO).toList();
    }

    @Override
    public Page<Friendship> getFriendshipsByIdAndStatus(UUID id, Friendship.Status status, int page, int size) {
        PageRequest pr = PageRequest.of(page,size);
        return friendshipRepository.getFriendshipsByIdAndStatus(id,status,pr);
    }

    private void friendshipModifyingValidation(UUID sender, UUID receiver)
    {
        Optional<AppUser> senderUser = appUserRepository.findById(sender);
        Optional<AppUser> receiverUser = appUserRepository.findById(receiver);

        if (senderUser.isEmpty() || receiverUser.isEmpty()) throw new IllegalArgumentException("Both users must exist.");
        if (sender.equals(receiver)) throw new IllegalArgumentException("Ids cannot be the same");
    }

    @Override
    public Boolean cancelFriendshipRequest(UUID sender, UUID receiver) {
        return friendshipRepository.deleteFriendshipRequest(sender, receiver, Friendship.Status.PENDING) == 1;
    }
    @Override
    public Boolean unfriend(UUID id1, UUID id2){
        return friendshipRepository.unfriend(id1,id2, Friendship.Status.ACCEPTED)==1;
    }
}
