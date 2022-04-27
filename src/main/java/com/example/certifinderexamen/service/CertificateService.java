package com.example.certifinderexamen.service;

import com.example.certifinderexamen.exception.BadRequestException;
import com.example.certifinderexamen.model.Certificate;
import com.example.certifinderexamen.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;


    public List<Certificate> getAllCertificates(){
        return certificateRepository.findAll();
    }

    public void addCertificate(Certificate certificate){
        Boolean existsCertType = certificateRepository
                .selectExistsCertType(certificate.getCertType());
        if(existsCertType){
            throw new BadRequestException(
                    "Certifikatet " + certificate.getCertType() + " finns redan i systemet"
            );
        }
        certificateRepository.save(certificate);
    }
}