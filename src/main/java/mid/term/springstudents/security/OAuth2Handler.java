package mid.term.springstudents.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mid.term.springstudents.model.User;
import mid.term.springstudents.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2Handler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Handler.class);

    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        try {
            logger.info("✅ OAuth2 authentication successful");

            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();


            // logger.info("OAuth2User attributes: {}", oAuth2User.getAttributes());

            String email = oAuth2User.getAttribute("email");
            if (email == null) {
                logger.error(" Email not found in OAuth2 attributes");
                response.sendRedirect("http://localhost:3000/login?error=email_not_provided");
                return;
            }

            Optional<User> userOptional = userRepository.findByEmail(email);
            User user = userOptional.orElseGet(() -> createNewUser(oAuth2User, email));

            String token = jwtTokenService.generateToken(user);


            logger.info("✅ Generated JWT token: {}", token);

            String redirectUrl = String.format(
                    "http://localhost:3000/oauth2/success?token=%s&email=%s",
                    token,
                    user.getEmail()
            );
            logger.info("🔁 Redirecting to: {}", redirectUrl);

            response.sendRedirect(redirectUrl);

        } catch (Exception e) {
            logger.error(" OAuth2 processing error", e);
            response.sendRedirect("http://localhost:3000/login?error=oauth_processing_error");
        }
    }


    private User createNewUser(OAuth2User oAuth2User, String email) {
        String name = oAuth2User.getAttribute("name");
        User user = new User();
        user.setUsername(name != null ? name : email.split("@")[0]);
        user.setEmail(email);
        user.setPassword("");
        user.setRole("USER");

        User savedUser = userRepository.save(user);
        logger.info("Created new user from OAuth2: {}", savedUser);
        return savedUser;
    }
}