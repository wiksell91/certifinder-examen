//package com.example.certifinderexamen.service;
//
//import com.example.certifinderexamen.model.Company;
//import com.example.certifinderexamen.repository.CertuserRepository;
//import com.example.certifinderexamen.repository.CompanyRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class UserService implements UserDetailsService {
//
//    private final CertuserRepository certuserRepository;
//    private final CompanyRepository companyRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Certuser certuser = certuserRepository.findCertuserByEmail(username);
//        Company company = companyRepository.findCompanyByUsername(username);
//
//
//        if(null==company) {
//            throw new UsernameNotFoundException("User Not Found with userName "+username);
//        }
//        return company;
//
////        if(certuser.getUsername().equals(username)){
////            return certuser;
////        }
////        if(company.getUsername().equals(username)){
////            return company;
////        }
////        else {
////            throw new UsernameNotFoundException("Användaren hittades inte");
////        }
//
//
//
//    }
//}
//
