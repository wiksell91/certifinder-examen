package com.example.certifinderexamen.service;

import com.example.certifinderexamen.exception.BadRequestException;
import com.example.certifinderexamen.model.Authority;
import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.repository.CertstatusRepository;
import com.example.certifinderexamen.repository.CertuserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class CertuserService {


    private final CertuserRepository certuserRepository;
    private final CertstatusRepository certstatusRepository;




    public List<Certuser> getAllCertuser(){
        return certuserRepository.findAll();
    }


    public Certuser addCertuser(Certuser certuser) {
        Boolean existsEmail = certuserRepository
                .selectExistsUsername(certuser.getUsername());
        if (existsEmail){
            throw new BadRequestException(
                    "Email " + certuser.getUsername() + " är upptaget");
        }


        return certuserRepository.save(certuser);
    }

    private Authority createAuthority(String roleCode, String roleDescription) {
        Authority authority=new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }


    public Certuser getCertuser(String email){
        return certuserRepository.findCertuserByUsername(email);
    }


    public void deleteCertuser(Long certuserId){
        Optional<Certuser> certuser = certuserRepository.findById(certuserId);

        List<Certstatus> certstatuse =
                certstatusRepository.findCertificatestatusByCertuser(certuser.get());
        certstatusRepository.deleteAll(certstatuse);
        certuserRepository.deleteById(certuserId);

    }
}
