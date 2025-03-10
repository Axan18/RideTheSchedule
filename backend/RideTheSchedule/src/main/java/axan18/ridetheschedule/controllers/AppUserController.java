package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.services.AppUserService;
import axan18.ridetheschedule.services.FriendshipService;
import axan18.ridetheschedule.services.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class AppUserController {

    private final AppUserService appUserService;
    private final JwtService jwtService;
    private final FriendshipService friendshipService;

    public static final String USER_PATH = "/users";
    public static final String USER_PATH_FRIENDS = "/users/friends";
    public static final int PAGE_SIZE = 20;

    @GetMapping(USER_PATH)
    ResponseEntity getAllUsers(@CookieValue(name = "JWT") String token, @PageableDefault(size = PAGE_SIZE)Pageable pageable){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        return ResponseEntity.ok(appUserService.listUsersWithoutMe(userID,pageable));
    }
    @GetMapping(USER_PATH_FRIENDS)
    ResponseEntity getFriends(@CookieValue(name = "JWT") String token, @PageableDefault(size = PAGE_SIZE)Pageable pageable){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        return ResponseEntity.ok(friendshipService.getFriendshipsById(userID));
    }
    @PostMapping(USER_PATH_FRIENDS+"/add/{userId}")
    ResponseEntity sendFriendshipRequest(@CookieValue(name="JWT") String token, @PathVariable(name = "userId") String friendId){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        Optional<Friendship> friendship = friendshipService.sendFriendshipRequest(userID, UUID.fromString(friendId));
        if(friendship.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(USER_PATH_FRIENDS+"/cancel/{userId}")
    ResponseEntity cancelFriendshipRequest(@CookieValue(name="JWT") String token, @PathVariable(name = "userId") String friendId){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        Boolean isCanceled = friendshipService.cancelFriendshipRequest(userID, UUID.fromString(friendId));
        if(!isCanceled)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(USER_PATH_FRIENDS+"/delete/{userId}")
    ResponseEntity unfriend(@CookieValue(name="JWT") String token, @PathVariable(name = "userId") String friendId){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        Boolean isDeleted = friendshipService.unfriend(userID, UUID.fromString(friendId));
        if(!isDeleted)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }
    @PutMapping(USER_PATH_FRIENDS+"/accept/{userId}")
    ResponseEntity acceptFriendship(@CookieValue(name="JWT") String token, @PathVariable(name = "userId") String friendId){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        Optional<Friendship> friendship = friendshipService.establishFriendship(UUID.fromString(friendId), userID);
        if(friendship.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().build();
    }
    @GetMapping(USER_PATH+"/get_name")
    ResponseEntity getUsername(@CookieValue(name="JWT", required = false) String token){
        if(token==null) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        String username = appUserService.getUsernameByID(userID);
        if(username!=null && !username.isEmpty())
            return ResponseEntity.ok().body(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}