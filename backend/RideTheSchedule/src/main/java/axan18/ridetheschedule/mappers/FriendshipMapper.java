package axan18.ridetheschedule.mappers;


import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.models.FriendshipDTO;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.FriendshipRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FriendshipMapper {

    @Autowired
    protected AppUserRepository appUserRepository; // Wstrzyknięcie repozytorium

    @Mapping(target = "id", expression = "java(new Friendship.FriendshipId(friendshipDTO.getUser1Id(), friendshipDTO.getUser2Id()))")
    @Mapping(target = "user1", expression = "java(getUserById(friendshipDTO.getUser1Id()))")
    @Mapping(target = "user2", expression = "java(getUserById(friendshipDTO.getUser2Id()))")
    public abstract Friendship toFriendship(FriendshipDTO friendshipDTO);

    @Mapping(target = "user1Id", source = "user1.id")
    @Mapping(target = "user2Id", source = "user2.id")
    public abstract FriendshipDTO toFriendshipDTO(Friendship friendship);

    protected AppUser getUserById(UUID id) {
        return appUserRepository.findById(id).orElseThrow();
    }
}
