package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.model.Certificate;
import com.example.certifinderexamen.service.CertificateService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/certificate")
@RestController
public class CertificateController {

    private final CertificateService certificateService;


    @GetMapping
    public List<Certificate> getAllCertificates(){
        return certificateService.getAllCertificates();
    }

    @PostMapping
    public void addCertificate(@RequestBody Certificate certificate){
        certificateService.addCertificate(certificate);
    }
}