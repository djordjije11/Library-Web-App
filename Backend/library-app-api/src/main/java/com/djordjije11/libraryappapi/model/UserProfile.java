package com.djordjije11.libraryappapi.model;

import com.djordjije11.libraryappapi.exception.ModelInvalidException;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a profile of a library system user.
 * Consists of id, username, password and role.
 *
 * @author Djordjije Radovic
 */
@Entity
public class UserProfile implements UserDetails {
    /**
     * A unique identification number, primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * A unique username. Must not be null and must not be longer than 30 characters.
     */
    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String username;
    /**
     * A user's password. Must not be null and must not be longer than 72 characters.
     */
    @Column(nullable = false, columnDefinition = "nvarchar(72)")
    private String password;
    /**
     * A user's role in a library system. Can be Employee or Admin.
     */
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserProfile() {
    }

    public UserProfile(String username, String password, UserRole role) {
        setUsername(username);
        setPassword(password);
        setRole(role);
    }

    /**
     * Returns the id, unique identification number.
     *
     * @return id unique identification number, primary key.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id, unique identification number.
     *
     * @param id unique identification number, primary key.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's role in the library system.
     *
     * @return role of the user in the library system.
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the user's role in the library system. Can be employee or admin.
     *
     * @param role of the user in the library system.
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Returns the username.
     *
     * @return username of the user.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the unique username. Must not be null and must not be longer than 30 characters.
     *
     * @param username of the user.
     * @throws ModelInvalidException when the username is null or longer than 30 characters.
     */
    public void setUsername(String username) {
        if (username == null || username.length() > 30) {
            throw new ModelInvalidException(UserProfile.class, "UserProfile's username must not be null and must not be longer than 30 characters.");
        }
        this.username = username;
    }

    /**
     * Returns the user's password.
     *
     * @return password of the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password. Must not be null and must not be longer than 72 characters.
     *
     * @param password of the user.
     * @throws ModelInvalidException when the password is null or longer than 72 characters.
     */
    public void setPassword(String password) {
        if (password == null || password.length() > 30) {
            throw new ModelInvalidException(UserProfile.class, "UserProfile's password must not be null and must not be longer than 72 characters.");
        }
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
