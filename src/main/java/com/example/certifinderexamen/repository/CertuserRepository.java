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
            "WHERE c.username = ?1"
    )
    Boolean selectExistsUsername(String username);

    Certuser findCertuserByUsername(String username);

    Optional<Certuser> findByUsername(String username);

}
