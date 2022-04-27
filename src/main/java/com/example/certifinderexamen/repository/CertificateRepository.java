package com.example.certifinderexamen.repository;

import com.example.certifinderexamen.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {


    @Query("" +
            "SELECT CASE WHEN COUNT(c) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Certificate c " +
            "WHERE c.certType = ?1"
    )
    Boolean selectExistsCertType(String certType);

    Optional<Certificate> findCertByCertType(String certType);

    @Query("SELECT c FROM Certificate c WHERE c.certType = ?1")
    Optional<Certificate> findCertByType(String certType);
}
