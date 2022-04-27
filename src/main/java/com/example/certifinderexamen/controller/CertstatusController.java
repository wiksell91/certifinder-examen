package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.service.CertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/certstatus")
public class CertstatusController {


    private final CertService certService;


    @GetMapping
    public List<Certstatus> getAllCertstatus(){
        return certService.getAllCertstatus();
    }


    @GetMapping("/cert/{certType}")
    public List<Certstatus> getCert(@PathVariable("certType") String certType){
        return certService.getCert(certType);
    }

    @GetMapping("/user/{username}")
    public List<Certstatus> getUsersCert(@PathVariable("username") String username){
        return certService.getUsersCert(username);
    }

    @PostMapping("/addstatus/user/{certuserId}/cert/{certificateId}")
    public void addCertstatus(@RequestBody Certstatus certstatus, @PathVariable("certuserId") Long certuserId, @PathVariable("certificateId") Long certificateId){
        certService.addCertstatus(certstatus, certuserId, certificateId);
    }

}
