package com.example.certifinderexamen.service;


import com.example.certifinderexamen.model.Certuser;

import com.example.certifinderexamen.repository.CertuserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    @Autowired
    private CertuserRepository certuserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Certuser> userOpt = certuserRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }else {
            return userOpt.get();
        }

    }
}