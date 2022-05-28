package com.example.certifinderexamen.service;

import com.example.certifinderexamen.exception.ResourceNotFoundException;
import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.model.Orderreq;
import com.example.certifinderexamen.repository.CertstatusRepository;
import com.example.certifinderexamen.repository.CertuserRepository;
import com.example.certifinderexamen.repository.OrderreqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderreqService {

    private final OrderreqRepository orderreqRepository;
    private final CertuserRepository certuserRepository;
    private final CertstatusRepository certstatusRepository;



    public Orderreq getById(Long craftsonUserId) throws ResourceNotFoundException {
        Orderreq orderreq = orderreqRepository.findById(craftsonUserId)
                .orElseThrow(() -> new ResourceNotFoundException("USER_NOT_FOUND"));
        return orderreq;
    }

    public List<Object> getAllOrderReqs(){
        return orderreqRepository.findAllOrderReqs();
    }


    public List<Orderreq> getAllOrderreq(){
        return orderreqRepository.findAll();
    }


    public void addOrderReqs(Orderreq orderreq, Long personId, Long companyId, Long certstatusId){
        Certuser person = certuserRepository.findById(personId).get();
        orderreq.setPerson(person);

        Certuser company = certuserRepository.findById(companyId).get();
        orderreq.setCompany(company);

        Certstatus status = certstatusRepository.findById(certstatusId).get();

        orderreq.setOrdertype(status.getCertificate().getCertType());

        orderreqRepository.save(orderreq);

    }

        public List<Orderreq> getCompanyOrders(String username){
        return orderreqRepository.findOrderreqByCompany(username);
    }

//    public List<Orderreq> getCompanyOrders(Long id){
//        return orderreqRepository.findOrderreqByCompany(id);
//    }

//    public List<Orderreq> getCompanyOrdersByName(String username){
//        Certuser company = certuserRepository.findByUsername(username).get();
//        return orderreqRepository.findOrderreqByCompanyUsername(company);
//    }


    public List<Orderreq> getUsersOrders(String username){
        return orderreqRepository.findOrderreqByUser(username);
    }

    public Orderreq updateOrder(Orderreq orderreq, Long id) {

        Orderreq order = orderreqRepository.findById(id).get();

        order.setOrderstatus(orderreq.getOrderstatus());
        order.setComment(orderreq.getComment());

        return orderreqRepository.save(order);
    }

//    public Orderreq updateStatus(Orderreq orderreq, Map<String, Object> fields) throws ParseException {
//        fields.forEach((key, value) -> {
//            Field field = ReflectionUtils.findField(Orderreq.class, (String) key);
//            field.setAccessible(true);
//            ReflectionUtils.setField(field, orderreq, value);
//        });
//
//        return orderreqRepository.save(orderreq);
//    }

}

