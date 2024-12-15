package com.fredrick.financial_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fredrick.financial_management.enumeration.AccountRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;



@Data
@Entity
@Table(name="account")
@Builder
public class Account implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private int id;
    @Column(name = "uuid",nullable = false)
    private String uuid;
    @Column(name = "name",nullable = false)
    private String name;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "email", unique = true,nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @OneToMany(mappedBy = "account",fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Item> items;
    @Column(name = "role",nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountRole role;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public Account(int id,
            String uuid,
            String name,
            String email,
            String password,
            List<Item> items,
            AccountRole role) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.items = items;
        this.role = role;
    }

    public Account() {
    }
}
