package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileTest {
    private UserProfile userProfile;

    @BeforeEach
    public void init() {
        userProfile = new UserProfile();
    }

    @Test
    public void testSetId() {
        long id = 1;
        userProfile.setId(id);
        assertEquals(id, userProfile.getId());
    }

    @Test
    void testSetUsername() {
        String username = "user123";
        userProfile.setUsername(username);
        assertEquals(username, userProfile.getUsername());
    }

    @Test
    void testSetUsernameNull() {
        assertThrows(ModelInvalidException.class, () -> userProfile.setUsername(null));
    }

    @Test
    void testSetUsernameTooLong() {
        String username = "a".repeat(31);
        assertThrows(ModelInvalidException.class, () -> userProfile.setUsername(username));
    }

    @Test
    void testSetPassword() {
        String password = "securePassword123";
        userProfile.setPassword(password);
        assertEquals(password, userProfile.getPassword());
    }

    @Test
    void testSetPasswordNull() {
        assertThrows(ModelInvalidException.class, () -> userProfile.setPassword(null));
    }

    @Test
    void testSetPasswordTooLong() {
        String password = "a".repeat(73);
        assertThrows(ModelInvalidException.class, () -> userProfile.setPassword(password));
    }

    @Test
    void testSetRole() {
        UserRole role = UserRole.ADMIN;
        userProfile.setRole(role);
        assertEquals(role, userProfile.getRole());
    }

    @Test
    void testGetAuthorities() {
        UserRole role = UserRole.EMPLOYEE;
        userProfile.setRole(role);
        Collection<? extends GrantedAuthority> authorities = userProfile.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(role.name())));
    }
}