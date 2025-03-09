package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.entities.Friendship;
import axan18.ridetheschedule.services.AppUserService;
import axan18.ridetheschedule.services.FriendshipService;
import axan18.ridetheschedule.services.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public static final String USER_PATH_ID = USER_PATH+"/{userId}";
    public static final int PAGE_SIZE = 20;

    @GetMapping(USER_PATH)
    ResponseEntity getAllUsers(@CookieValue(name = "JWT") String token, @PageableDefault(size = PAGE_SIZE)Pageable pageable){
        return ResponseEntity.ok(appUserService.listUsers(pageable));
    }
    @GetMapping(USER_PATH+"/friends")
    ResponseEntity getFriends(@CookieValue(name = "JWT") String token, @PageableDefault(size = PAGE_SIZE)Pageable pageable){
        Claims claims = jwtService.parseToken(token);
        UUID userID = UUID.fromString(claims.getSubject());
        return ResponseEntity.ok(friendshipService.getFriendshipsById(userID));
    }

}
