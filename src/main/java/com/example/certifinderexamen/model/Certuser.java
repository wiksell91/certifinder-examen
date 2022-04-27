package com.example.certifinderexamen.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table
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
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    //@Column(nullable = false)
    private String fullName;
    private Date createdAt;
    private Date updatedAt;
    private boolean enabled;


    //@Column(nullable = false)
    private String city;



    @OneToMany(mappedBy = "certuser",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Certstatus> certstatuses = new ArrayList<>();


    @OneToMany(mappedBy = "certuser",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Orderreq> orderreqs = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
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
