package axan18.ridetheschedule.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(Object principal) {
        String email = null;
        String name = null;
        String picture = null;

        if (principal instanceof OAuth2User oAuth2User) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");
            picture = oAuth2User.getAttribute("picture");
        } else if (principal instanceof UserDetails userDetails) {
            email = userDetails.getUsername();
        }

        if (email == null) {
            throw new IllegalArgumentException("Email not found in authentication principal");
        }

        return Jwts.builder()
                .setSubject(email)
                .claim("name", name)
                .claim("picture", picture)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public Claims parseToken(String token) { //TODO: error handling
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = parseToken(token);
            return !claims.getExpiration().before(new Date()); // Czy token nie wygasł
        } catch (ExpiredJwtException e) {
            System.out.println("Token wygasł: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("Błąd walidacji JWT: " + e.getMessage());
        }
        return false;
    }
}