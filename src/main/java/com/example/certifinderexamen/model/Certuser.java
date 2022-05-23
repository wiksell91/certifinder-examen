package com.example.certifinderexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Certuser implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

//    @OneToMany(mappedBy = "certuser",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    private List<Orderreq> orderreqs = new ArrayList<>();



//    @OneToMany(mappedBy = "company",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true)
//    private List<Orderreq> companyorderreqs = new ArrayList<>();
//
//
    @JsonIgnore
    @OneToMany(mappedBy = "certuser",
            cascade = CascadeType.MERGE,fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Certstatus> certstatuses = new ArrayList<>();

    private String fullName;
    private String username;
    private String password;
    private String city;
    private Boolean enabled = true;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "certuser", cascade = CascadeType.ALL)
    private Set<Authorities> authorities = new HashSet<>();


    @Override
    public Set<Authorities> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authorities> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}