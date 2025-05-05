package mid.term.springstudents.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {

        User user = new User("testUser", "test@example.com", "password123", "USER");


        assertNull(user.getId()); // ID должен быть null до сохранения в БД
        assertEquals("testUser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    void testEmptyConstructor() {

        User user = new User();


        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void testSettersAndGetters() {

        User user = new User();


        user.setId(1L);
        user.setUsername("newUser");
        user.setEmail("new@example.com");
        user.setPassword("newPassword");
        user.setRole("ADMIN");


        assertEquals(1L, user.getId());
        assertEquals("newUser", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testGetAuthoritiesForUserRole() {

        User user = new User();
        user.setRole("USER");


        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();


        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void testGetAuthoritiesForAdminRole() {

        User user = new User();
        user.setRole("ADMIN");


        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();


        assertEquals(1, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.iterator().next().getAuthority());
    }


    @Test
    void testEntityAnnotations() throws NoSuchFieldException {

        assertNotNull(User.class.getAnnotation(Entity.class));


        assertNotNull(User.class.getDeclaredField("id").getAnnotation(Id.class));
        assertNotNull(User.class.getDeclaredField("id").getAnnotation(GeneratedValue.class));
        assertEquals(GenerationType.IDENTITY,
                User.class.getDeclaredField("id").getAnnotation(GeneratedValue.class).strategy());
    }
}