package com.example.certifinderexamen.controller.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthCredentialsRequest {

    private long id;
    private String fullName;
    private String username;
    private String password;

}
