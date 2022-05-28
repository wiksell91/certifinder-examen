package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.exception.ResourceNotFoundException;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.service.CertuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@RestController
public class CertuserController {

    private final CertuserService certuserService;


    @GetMapping
    public List<Certuser> getAllAccounts(){
        return certuserService.getAllUsers();
    }

    @GetMapping("/{username}")
    public Certuser getUserByUserName(@PathVariable("username") String username){
        return certuserService.getUserByUserName(username);
    }

    @PostMapping("/company")
    public void addCompany(@RequestBody Certuser certuser){
        certuserService.addCompany(certuser);
    }

    @PostMapping("/user")
    public void addUser(@RequestBody Certuser certuser){
        certuserService.addUser(certuser);
    }

    @PatchMapping("/update/{username}")
    public Certuser updateUser(@PathVariable String username, @RequestBody Map<String, Object> fields) throws ResourceNotFoundException, ParseException {
        Certuser certuser = certuserService.getUserByUserName(username);
        return certuserService.updateUser(certuser, fields);
    }


    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable ("username") String username){
        certuserService.deleteUser(username);
    }


    @DeleteMapping("/deletecomp/{username}")
    public void deleteCompany(@PathVariable ("username") String username){
        certuserService.deleteCompany(username);
    }


}
