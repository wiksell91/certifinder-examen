package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.service.CertService;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @GetMapping("/user/{userId}")
    public List<Certstatus> getUsersCert(@PathVariable("userId") Long userId){
        return certService.getUsersCert(userId);
    }

    @PostMapping("/addstatus/user/{username}/cert/{certificateId}")
    public void addCertstatus(@RequestBody Certstatus certstatus, @PathVariable("username") String username, @PathVariable("certificateId") Long certificateId){
        certService.addCertstatus(certstatus, username, certificateId);
    }

}
