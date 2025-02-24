package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendshipServiceJPA implements FriendshipService {

    private final AppUserRepository appUserRepository;
    private final FriendshipRepository friendshipRepository;

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

        friendshipRepository.establishFriendship(sender,receiver, Friendship.Status.PENDING);
        return friendshipRepository.getFriendshipBetween(sender,receiver);
    }

    @Override
    public Page<Friendship> getFriendshipsById(UUID id, int page, int size) {
        PageRequest pr = PageRequest.of(page,size);
        return friendshipRepository.getFriendshipsById(id,pr);
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
}
