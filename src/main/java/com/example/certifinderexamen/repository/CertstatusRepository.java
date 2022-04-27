package com.example.certifinderexamen.repository;

import com.example.certifinderexamen.model.Certificate;
import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.model.Certuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertstatusRepository extends JpaRepository<Certstatus, Long> {

    List<Certstatus> findCertificatestatusByCertificate(Certificate certificate);

    List<Certstatus> findCertificatestatusByCertuser(Certuser certuser);



}