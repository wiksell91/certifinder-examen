package com.example.certifinderexamen.service;

import com.example.certifinderexamen.exception.BadRequestException;
import com.example.certifinderexamen.model.Authority;
import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.repository.CertstatusRepository;
import com.example.certifinderexamen.repository.CertuserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;



    public List<Certuser> getAllCertuser(){
        return certuserRepository.findAll();
    }


    public Certuser addCertuser(Certuser certuser) {
        Boolean existsEmail = certuserRepository
                .selectExistsEmail(certuser.getEmail());
        if (existsEmail){
            throw new BadRequestException(
                    "Email " + certuser.getEmail() + " är upptaget");
        }

        certuser.setPassword(passwordEncoder.encode(certuser.getPassword()));
        return certuserRepository.save(certuser);
    }

    private Authority createAuthority(String roleCode, String roleDescription) {
        Authority authority=new Authority();
        authority.setRoleCode(roleCode);
        authority.setRoleDescription(roleDescription);
        return authority;
    }


    public Certuser getCertuser(String email){
        return certuserRepository.findCertuserByEmail(email);
    }


    public void deleteCertuser(Long certuserId){
        Optional<Certuser> certuser = certuserRepository.findById(certuserId);

        List<Certstatus> certstatuse =
                certstatusRepository.findCertificatestatusByCertuser(certuser.get());
        certstatusRepository.deleteAll(certstatuse);
        certuserRepository.deleteById(certuserId);

    }
}
