package com.example.certifinderexamen.service;

import com.example.certifinderexamen.exception.BadRequestException;
import com.example.certifinderexamen.model.Authority;
import com.example.certifinderexamen.model.Company;
import com.example.certifinderexamen.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Service
public class CompanyService  {


    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;


    public Company getCompanyByUserName(String username){
        return companyRepository.findCompanyByUsername(username);
    }

    public List<Company> getAllCompany(){
        return companyRepository.findAll();
    }

    public void addCompany(Company company){
        Boolean existsCompany = companyRepository
                .selectExistsCompany(company.getFullName());

        if (existsCompany){
            throw new BadRequestException(
                    "Företaget " + company.getFullName() + " finns redan i systemet"
            );
        }
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(createAuthority("Company", "Company role"));
        company.setAuthorities(authorityList);
        company.setPassword(passwordEncoder.encode(company.getPassword()));
        companyRepository.save(company);
    }

    private Authority createAuthority(String roleCode,String roleDescription) {
        Authority authority=new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }
}