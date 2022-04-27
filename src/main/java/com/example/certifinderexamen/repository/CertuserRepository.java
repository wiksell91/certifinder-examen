package com.example.certifinderexamen.repository;

import com.example.certifinderexamen.model.Certuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;



@Repository
@Transactional(readOnly = true)
public interface CertuserRepository extends JpaRepository<Certuser, Long> {


    @Query("" +
            "SELECT CASE WHEN COUNT(c) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Certuser c " +
            "WHERE c.email = ?1"
    )
    Boolean selectExistsEmail(String email);

    Certuser findCertuserByEmail(String email);

    Optional<Certuser> findByEmail(String email);

}
