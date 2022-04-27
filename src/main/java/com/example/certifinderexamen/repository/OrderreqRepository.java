package com.example.certifinderexamen.repository;

import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.model.Company;
import com.example.certifinderexamen.model.Orderreq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderreqRepository extends JpaRepository<Orderreq, Long> {

    List<Orderreq> findOrderreqByCompany(Company company);
    List<Orderreq> findOrderreqByCertuser(Certuser certuser);
}

