package com.example.certifinderexamen.controller;

import com.example.certifinderexamen.exception.ResourceNotFoundException;
import com.example.certifinderexamen.model.Orderreq;
import com.example.certifinderexamen.service.OrderreqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/orderreq")
public class OrderreqController {

    private final OrderreqService orderreqService;


    @GetMapping
    public List<Object> getAllOrderReqs(){
        return orderreqService.getAllOrderReqs();
    }

    @GetMapping("/all")
    public List<Orderreq> getAllOrderReq(){
        return orderreqService.getAllOrderreq();
    }



    @GetMapping("/user")
    public List<Orderreq> getUserOrders(@RequestParam("username") String username){
        return orderreqService.getUsersOrders(username);
    }


        @GetMapping("/company")
    public List<Orderreq> getCompanyOrders(@RequestParam("username") String username){
        return orderreqService.getCompanyOrders(username);
    }


//    @GetMapping("/company/{id}")
//    public List<Orderreq> getCompanyOrders(@PathVariable("id") Long id){
//        return orderreqService.getCompanyOrders(id);
//    }

//    @GetMapping("/company")
//    public List<Orderreq> getCompanyOrdersByName(@RequestParam("username") String username){
//        return orderreqService.getCompanyOrdersByName(username);
//    }


    @PostMapping("/addorder/user/{personId}/company/{companyId}/cert/{certstatusId}")
    public void addOrderReqs(@RequestBody Orderreq orderreq, @PathVariable("personId") Long personId,
                             @PathVariable("companyId") Long companyId, @PathVariable("certstatusId") Long certstatusId){
        orderreqService.addOrderReqs(orderreq, personId, companyId, certstatusId);
    }

    @PutMapping("/updatestatus/{id}")
    public Orderreq updateOrder (@RequestBody Orderreq orderreq, @PathVariable Long id){

        return orderreqService.updateOrder(orderreq, id);
    }



}