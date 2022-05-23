package com.example.certifinderexamen.repository;


import com.example.certifinderexamen.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authorities, Long> {

    @Query("select a from Authorities a where a.certuser.id = ?1")
    List<Authorities> findAllByUserId(Long id);

}
