package com.example.certifinderexamen.service;

import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.model.Company;
import com.example.certifinderexamen.model.Orderreq;
import com.example.certifinderexamen.repository.CertuserRepository;
import com.example.certifinderexamen.repository.CompanyRepository;
import com.example.certifinderexamen.repository.OrderreqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderreqService {

    private final CertuserRepository certuserRepository;
    private final OrderreqRepository orderreqRepository;
    private final CompanyRepository companyRepository;



    public List<Orderreq> getAllOrderReqs(){
        return orderreqRepository.findAll();
    }


    public void addOrderReqs(Orderreq orderreq, Long certuserId, Long companyId){
        Certuser certuser = certuserRepository.findById(certuserId).get();
        orderreq.setCertuser(certuser);
        Company company = companyRepository.findById(companyId).get();
        orderreq.setCompany(company);

        orderreqRepository.save(orderreq);

    }

    public List<Orderreq> getCompanyOrders(Long id){
        Optional<Company> comp = companyRepository.findById(id);

        return orderreqRepository.findOrderreqByCompany(comp.get());
    }

    public List<Orderreq> getUsersOrders(Long id){
        Optional<Certuser> certuser = certuserRepository.findById(id);
        return orderreqRepository.findOrderreqByCertuser(certuser.get());
    }

}

