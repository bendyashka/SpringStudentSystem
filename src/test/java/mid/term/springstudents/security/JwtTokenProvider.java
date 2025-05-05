package mid.term.springstudents.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private final String testUsername = "test@example.com";
    private final String secretKey = Base64.getEncoder().encodeToString("very-secret-key-for-testing-1234567890".getBytes());
    private final long accessTokenExpiration = 3600000; // 1 hour
    private final long refreshTokenExpiration = 86400000; // 24 hours

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secretKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", accessTokenExpiration);
        ReflectionTestUtils.setField(jwtTokenProvider, "refreshExpirationMs", refreshTokenExpiration);
    }

    @Test
    void generateAccessToken_ShouldReturnValidToken() {

        String token = jwtTokenProvider.generateAccessToken(testUsername);


        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(testUsername, jwtTokenProvider.getUsernameFromToken(token));
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void generateRefreshToken_ShouldReturnValidToken() {

        String token = jwtTokenProvider.generateRefreshToken(testUsername);


        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(testUsername, jwtTokenProvider.getUsernameFromToken(token));
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void getUsernameFromToken_ShouldReturnCorrectUsername() {

        String token = jwtTokenProvider.generateAccessToken(testUsername);


        String username = jwtTokenProvider.getUsernameFromToken(token);


        assertEquals(testUsername, username);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {

        String token = jwtTokenProvider.generateAccessToken(testUsername);


        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_ShouldReturnFalseForExpiredToken() throws InterruptedException {

        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 1); // 1 ms
        String token = jwtTokenProvider.generateAccessToken(testUsername);
        TimeUnit.MILLISECONDS.sleep(2);


        assertFalse(jwtTokenProvider.validateToken(token));
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidToken() {

        String invalidToken = "invalid.token.string";


        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    void validateToken_ShouldThrowExceptionForMalformedToken() {

        String malformedToken = "header.payload.signature";


        assertThrows(MalformedJwtException.class, () -> {
            jwtTokenProvider.getUsernameFromToken(malformedToken);
        });
    }

    @Test
    void validateToken_ShouldThrowExceptionForUnsignedToken() {

        String unsignedToken = Jwts.builder()
                .setSubject(testUsername)
                .setIssuedAt(new Date())
                .compact();


        assertThrows(UnsupportedJwtException.class, () -> {
            jwtTokenProvider.getUsernameFromToken(unsignedToken);
        });
    }

    @Test
    void getUsernameFromToken_ShouldThrowExceptionForExpiredToken() throws InterruptedException {

        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 1); // 1 ms
        String token = jwtTokenProvider.generateAccessToken(testUsername);
        TimeUnit.MILLISECONDS.sleep(2); // Ждем, чтобы токен точно истек


        assertThrows(ExpiredJwtException.class, () -> {
            jwtTokenProvider.getUsernameFromToken(token);
        });
    }
}