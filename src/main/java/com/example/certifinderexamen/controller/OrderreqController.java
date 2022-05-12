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


    @GetMapping("/company/{id}")
    public List<Orderreq> getCompanyOrders(@PathVariable("id") Long id){
        return orderreqService.getCompanyOrders(id);
    }

    @GetMapping("/user/{id}")
    public List<Orderreq> getUsersOrders(@PathVariable("id") Long id){
        return orderreqService.getUsersOrders(id);
    }

    @PostMapping("/addorder/user/{certuserId}/company/{companyId}")
    public void addOrderReqs(@RequestBody Orderreq orderreq, @PathVariable("certuserId") Long certuserId, @PathVariable("companyId") Long companyId){
        orderreqService.addOrderReqs(orderreq, certuserId, companyId);
    }
}