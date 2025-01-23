package com.fredrick.financial_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fredrick.financial_management.enumeration.AccountRole;
import com.fredrick.financial_management.enumeration.Country;
import com.fredrick.financial_management.enumeration.Gender;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;



@Data
@Entity
@Table(name="account")
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Account implements UserDetails{
    @Id
    @Column(name = "uuid",nullable = false, unique = true)
    private String uuid;
    @Column(name = "firstname",nullable = false)
    private String firstname;
    @Column(name = "lastname",nullable = false)
    private String lastname;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(name = "email", unique = true,nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    @JsonIgnore
    private String password;
    @Column(name = "gender",nullable = false)
    private String gender;
    @Column(name = "dob",nullable = true)
    private LocalDate dob;
    @Column(name = "phonenumber",nullable = true)
    private String phonenumber;
    @Column(name = "isActive",nullable = false)
    @Builder.Default
    private boolean isActive = true;
    @Column(name = "country",nullable = true)
    private String country;

    @JsonIgnore
    @Column(name = "createDate",nullable = true)
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;
    @JsonIgnore
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
    public Account() {
    }

    @Override
    public String toString() {
        return "Account{" + "uuid='" + uuid + '\'' + ", firstname='" + firstname + '\''
                + ", lastname='" + lastname + '\'' + ", email='" + email + '\'' + ", password='"
                + password + '\'' + ", gender=" + gender + ", dob=" + dob + ", phonenumber='"
                + phonenumber + '\'' + ", isActive=" + isActive + ", country=" + country
                + ", createdAt=" + createdAt + ", role=" + role + '}';
    }
}
