package com.example.certifinderexamen.service;


import com.example.certifinderexamen.model.Company;

import com.example.certifinderexamen.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Company> companyOpt = companyRepository.findByUsername(username);
        if (companyOpt.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }else {
            return companyOpt.get();
        }
//        Optional<Company> CompanyOpt = companyRepository.findByUsername(username);
//        if (CompanyOpt.isEmpty()) {
//            // user exists in Proffesso but not here, this means we'll need to create an account
//            //  for this user in this app
//            Company company = new Company(certuserOpt.get(), Optional.empty());
//            company = companyRepository.save(company);
//            return company;
//        } else {
//            // TODO: Check that proffesso user and this app's user are in sync.
//            return CompanyOpt.get();
//        }
//
//    }    }
    }
}