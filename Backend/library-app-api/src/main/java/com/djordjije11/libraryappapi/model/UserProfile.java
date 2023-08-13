package com.djordjije11.libraryappapi.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a profile of a library system user.
 * Consists of id, username, password and role.
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
     * A unique username. Should not be null and should not be longer than 30 characters.
     */
    @Column(nullable = false, unique = true, columnDefinition = "varchar(30)")
    private String username;
    /**
     * A user's password. Should not be null and should not be longer than 72 characters.
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
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the id, unique identification number.
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id, unique identification number.
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the user's role in the library system.
     * @return role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets the user's role in the library system. Can be employee or admin.
     * @param role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Returns the username.
     * @return username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Sets the unique username. Should not be null and should not be longer than 30 characters.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's password.
     * @return password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password. Should not be null and should not be longer than 72 characters.
     * @param password
     */
    public void setPassword(String password) {
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
