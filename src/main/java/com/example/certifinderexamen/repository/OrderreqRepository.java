package com.example.certifinderexamen.repository;


import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.model.Orderreq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderreqRepository extends JpaRepository<Orderreq, Long> {


//    @Query("SELECT o from Orderreq o WHERE o.company.id = ?1 ")
//    List<Orderreq> findOrderreqByCompany(Long id);

//    @Query("SELECT o.person.fullName, o.company.fullName, o.comment, o.orderdate from Orderreq o WHERE o.company.id = ?1 ")
//    List<Orderreq> findOrderreqByCompany(Long id);

    @Query("SELECT o from Orderreq o WHERE o.company.username = ?1 ")
    List<Orderreq> findOrderreqByCompany(String username);

    @Query("SELECT o.person, o.company.fullName, o.comment, o.orderdate from Orderreq o")
    List<Object> findAllOrderReqs();

//    List<Orderreq>findOrderreqByCompanyUsername(Certuser company);

    @Query("SELECT o from Orderreq o WHERE o.person.username = ?1 ")
    List<Orderreq> findOrderreqByUser(String username);

}

