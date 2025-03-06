package axan18.ridetheschedule.controllers;

import axan18.ridetheschedule.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;

    @Autowired
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(Principal principal) {
        if (principal == null) {
            System.out.println("Not logged in");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.generateToken(principal);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
