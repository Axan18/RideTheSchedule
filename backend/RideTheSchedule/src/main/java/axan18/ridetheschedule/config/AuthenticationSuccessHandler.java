package axan18.ridetheschedule.config;

import axan18.ridetheschedule.repositories.AppUserRepository;
import axan18.ridetheschedule.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final AppUserRepository appUserRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        String email = user.getAttribute("email");
        if (email == null) {
            throw new IllegalArgumentException("Email not found in authentication principal");
        }
        UUID userID = appUserRepository.getIdByEmail(email);
        String token = jwtService.generateToken(user, userID);
        ResponseCookie jwtCookie = ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(true) //https
                .sameSite("None")
                .path("/")
                .domain("localhost")
                .maxAge(Duration.ofHours(1))
                .build();

        response.setHeader("Set-Cookie", jwtCookie.toString());
        // Zwracamy stronę HTML z automatycznym przekierowaniem
        String htmlResponse = "<!DOCTYPE html>" +
                "<html lang='pl'>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<title>Logowanie...</title>" +
                "<script>" +
                "setTimeout(function() {" +
                "   window.location.href = 'https://localhost:8080/homepage.html';" +
                "}, 100); " +  // delay to save the cookie with jwt
                "</script>" +
                "</head>" +
                "<body>" +
                "<p>Logowanie zakończone sukcesem. Jeśli nie zostałeś przekierowany, <a href='https://localhost:8080/homepage.html'>kliknij tutaj</a>.</p>" +
                "</body>" +
                "</html>";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getHeaderNames().forEach(name ->
                System.out.println(name + ": " + response.getHeader(name))
        );

        response.getWriter().write(htmlResponse);
    }
}
