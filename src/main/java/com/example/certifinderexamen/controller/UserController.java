package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.config.JWTTokenHelper;
import com.example.certifinderexamen.model.Company;
import com.example.certifinderexamen.requests.AuthenticationRequest;
import com.example.certifinderexamen.responses.LoginResponse;
import com.example.certifinderexamen.responses.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenHelper jWTTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    //    @PostMapping("/auth/loginuser")
//    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                authenticationRequest.getUserName(), authenticationRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        Certuser user=(Certuser) authentication.getPrincipal();
//        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());
//
//        LoginResponse response=new LoginResponse();
//        response.setToken(jwtToken);
//
//
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/auth/logincompany")
    public ResponseEntity<?> loginCompany(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        Company user=(Company) authentication.getPrincipal();
        String jwtToken=jWTTokenHelper.generateToken(user.getUsername());

        LoginResponse response=new LoginResponse();
        response.setToken(jwtToken);


        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user){
        Company userObj=(Company) userDetailsService.loadUserByUsername(user.getName());

        UserInfo userInfo=new UserInfo();
        userInfo.setFullName(userObj.getFullName());
        userInfo.setRoles(userObj.getAuthorities().toArray());

        return ResponseEntity.ok(userInfo);

    }
}