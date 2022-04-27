package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.model.Orderreq;
import com.example.certifinderexamen.service.OrderreqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/orderreq")
public class OrderreqController {

    private final OrderreqService orderreqService;


    @GetMapping
    public List<Orderreq> getAllOrderReqs(){
        return orderreqService.getAllOrderReqs();
    }


    @GetMapping("/company/{companyname}")
    public List<Orderreq> getCompanyOrders(@PathVariable("companyname") String companyname){
        return orderreqService.getCompanyOrders(companyname);
    }

    @GetMapping("/user/{username}")
    public List<Orderreq> getUsersOrders(@PathVariable("username") String username){
        return orderreqService.getUsersOrders(username);
    }

    @PostMapping("/addorder/user/{certuserId}/company/{companyId}")
    public void addOrderReqs(@RequestBody Orderreq orderreq, @PathVariable("certuserId") Long certuserId, @PathVariable("companyId") Long companyId){
        orderreqService.addOrderReqs(orderreq, certuserId, companyId);
    }
}