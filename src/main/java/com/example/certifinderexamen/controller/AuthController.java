package com.example.certifinderexamen.controller;


import ch.qos.logback.core.util.Duration;
import com.example.certifinderexamen.controller.dto.AuthCredentialsRequest;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.repository.CertuserRepository;
import com.example.certifinderexamen.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CertuserRepository certuserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
//    @Value("${cookies.domain}")
//    private String domain;
//    @Value("${cookies.protocol")
//    private String protocol;

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody AuthCredentialsRequest request){

        Certuser user = certuserRepository.findCertuserByUsername(request.getUsername());



        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()));

            Certuser certuser = (Certuser) authenticate.getPrincipal();
            certuser.setPassword(null);


            String token = jwtUtil.generateToken(certuser);
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
//                    .domain(domain)
//                    .httpOnly(true)
//                    .secure(true)
                    .path("/")
                    .maxAge(Duration.buildByDays(365).getMilliseconds())
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(token);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


    }
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "jwt") String token,
                                           @AuthenticationPrincipal Certuser certuser) {
        try {
            Boolean isValidToken = jwtUtil.validateToken(token, certuser);
            return ResponseEntity.ok(isValidToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(Principal user){

        Certuser certuserObj =(Certuser) userDetailsService.loadUserByUsername(user.getName());
        AuthCredentialsRequest userInfo=new AuthCredentialsRequest();
        userInfo.setId(certuserObj.getId());
        userInfo.setUsername(certuserObj.getUsername());
        userInfo.setFullName(certuserObj.getFullName());


        return ResponseEntity.ok(userInfo);



    }

}
