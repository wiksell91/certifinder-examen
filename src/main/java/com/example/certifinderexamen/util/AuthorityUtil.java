package com.example.certifinderexamen.util;

import com.example.certifinderexamen.model.Certuser;

public class AuthorityUtil {

    public static Boolean hasRole(String role, Certuser certuser) {
        return certuser.getAuthorities()
                .stream()
                .filter(auth -> auth.getAuthority().equals(role))
                .count() > 0;
    }
}