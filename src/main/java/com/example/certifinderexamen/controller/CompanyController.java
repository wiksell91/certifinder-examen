package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.model.Company;
import com.example.certifinderexamen.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/api/v1/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;


    @GetMapping
    public List<Company> getAllCompany(){
        return companyService.getAllCompany();
    }

    @GetMapping("/{username}")
    public Company getCompanyByUserName(@PathVariable("username") String username){
        return companyService.getCompanyByUserName(username);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company){
        companyService.addCompany(company);
    }
}
