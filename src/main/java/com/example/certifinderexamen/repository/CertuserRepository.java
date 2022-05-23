package com.example.certifinderexamen.repository;

import com.example.certifinderexamen.model.Certificate;
import com.example.certifinderexamen.model.Certuser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;



@Repository
public interface CertuserRepository extends JpaRepository<Certuser, Long> {

    @Query("" +
            "SELECT CASE WHEN COUNT(c) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM  Certuser c " +
            "WHERE c.username = ?1"
    )
    Boolean selectExistsUser(String username);

    Certuser findCertuserByUsername(String username);

    Optional<Certuser> findByUsername(String username);

//    @Query("DELETE from Certuser c WHERE c.username = ?1")
//    Optional <Certuser> deleteCertuserByUsername(String username);


//    @Transactional
//    @Modifying
//    @Query("UPDATE Certuser u " +
//            "SET u.enabled = TRUE WHERE u.username = ?1")
//    int enableUser(String username);
}
