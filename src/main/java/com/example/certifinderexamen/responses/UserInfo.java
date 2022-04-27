package com.example.certifinderexamen.responses;

import lombok.Getter;
import lombok.Setter;


public class UserInfo {

    private String fullName;

    private String username;

    private Object roles;


    public String getfullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getRoles() {
        return roles;
    }

    public void setRoles(Object roles) {
        this.roles = roles;
    }

}