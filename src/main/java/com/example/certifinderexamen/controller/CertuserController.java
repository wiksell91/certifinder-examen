package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.service.CertuserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/certuser")
public class CertuserController {

    private final CertuserService certuserService;


    @GetMapping("/all")
    public List<Certuser> getAllCertuser(){
        return certuserService.getAllCertuser();
    }


    @PostMapping("/add")
    public ResponseEntity<Certuser> addCertuser(@RequestBody Certuser certuser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/certuser/add").toUriString());
        return ResponseEntity.created(uri).body(certuserService.addCertuser(certuser));
    }



    @DeleteMapping(path = "/{certuserId}")
    public void deleteCertuser(@PathVariable("certuserId") Long certuserId){
        certuserService.deleteCertuser(certuserId);
    }
}
