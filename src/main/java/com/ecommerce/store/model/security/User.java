package com.ecommerce.store.model.security;

import com.ecommerce.store.common.AuthenticationProvider;
import com.ecommerce.store.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @Size(min = 6, message = "Username must be at least 6 characters")
    @Pattern(regexp = "\\S+$", message = "Username is mandatory, cannot contain space")
    @NotNull(message = "Username must not be null")
    private String username;

    @NotNull(message = "Password must not be null")
    private String password;

    @Email(message = "Email Invalid, please try again.")
    private String email;
    private Long phoneNumber;

    @NotEmpty(message = "Full name must not be empty")
    private String fullName;
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    public User() {
    }

    public User(String username, String password, @Email(message = "Email Invalid, please try again.") String email,
                Long phoneNumber, String fullName, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.address = address;
    }
}