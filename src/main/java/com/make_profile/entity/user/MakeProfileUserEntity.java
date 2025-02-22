package com.make_profile.entity.user;

import jakarta.persistence.*;

@Entity
@Table(name="make_profile_user")
public class MakeProfileUserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String signInAccess;

    @Column
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignInAccess() {
        return signInAccess;
    }

    public void setSignInAccess(String signInAccess) {
        this.signInAccess = signInAccess;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

