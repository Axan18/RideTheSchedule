package axan18.ridetheschedule.services;

import axan18.ridetheschedule.entities.AppUser;
import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.repositories.FriendshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FriendshipServiceJPATest {

    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    FriendshipServiceJPA friendshipServiceJPA;

    AppUser user1;
    AppUser user2;
    Friendship newFriendship = Friendship.builder()
            .lastModified(Timestamp.valueOf(LocalDateTime.now()))
            .status(Friendship.Status.PENDING)
            .build();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        user1 = AppUser.builder()
                .id(UUID.randomUUID())
                .username("User1")
                .password("test")
                .email("test@example.xyz")
                .lastLoginDate(Date.valueOf("2021-01-01"))
                .isActive(true)
                .build();
        user2 = AppUser.builder()
                .id(UUID.randomUUID())
                .username("User2")
                .password("test")
                .email("test@example2.xyz")
                .lastLoginDate(Date.valueOf("2021-01-02"))
                .isActive(true)
                .build();
        newFriendship.setId(new Friendship.FriendshipId(user1.getId(),user2.getId()));
        newFriendship.setUser1(user1);
        newFriendship.setUser2(user2);
    }


    @Test
    void testEstablishFriendship(){
        when(appUserRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(appUserRepository.findById(user2.getId())).thenReturn(Optional.of(user2));

        assertThrows(IllegalArgumentException.class, ()->friendshipServiceJPA.establishFriendship(user1.getId(), UUID.randomUUID()));
        assertThrows(IllegalArgumentException.class, ()->friendshipServiceJPA.establishFriendship(user1.getId(), user1.getId()));
        assertThrows(IllegalStateException.class, ()->friendshipServiceJPA.establishFriendship(user1.getId(), user2.getId()));
        when(friendshipRepository.getFriendshipBetween(user1.getId(),user2.getId()))
                .thenReturn(Optional.of(newFriendship));
        assertDoesNotThrow(()->friendshipServiceJPA.establishFriendship(user1.getId(), user2.getId()));
    }
    @Test
    void testSendRequest(){
        when(appUserRepository.findById(user1.getId())).thenReturn(Optional.of(user1));
        when(appUserRepository.findById(user2.getId())).thenReturn(Optional.of(user2));

        assertThrows(IllegalArgumentException.class, ()->friendshipServiceJPA.sendFriendshipRequest(user1.getId(), UUID.randomUUID()));
        assertThrows(IllegalArgumentException.class, ()->friendshipServiceJPA.sendFriendshipRequest(user1.getId(), user1.getId()));
        assertDoesNotThrow(()->friendshipServiceJPA.sendFriendshipRequest(user1.getId(), user2.getId()));
        when(friendshipRepository.getFriendshipBetween(user1.getId(),user2.getId()))
                .thenReturn(Optional.of(newFriendship));
        assertThrows(IllegalStateException.class,()->friendshipServiceJPA.sendFriendshipRequest(user1.getId(), user2.getId()));
    }
}