package com.example.certifinderexamen.service;

import com.example.certifinderexamen.model.Certificate;
import com.example.certifinderexamen.model.Certstatus;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.repository.CertificateRepository;
import com.example.certifinderexamen.repository.CertstatusRepository;
import com.example.certifinderexamen.repository.CertuserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CertService {

    private  final CertstatusRepository certstatusRepository;
    private final CertificateRepository certificateRepository;
    private final CertuserRepository certuserRepository;



    public List<Certstatus> getAllCertstatus(){
        return certstatusRepository.findAll();
    }

    public void addCertstatus(Certstatus certstatus, Long certuserId, Long certificateId){
        Certuser certuser = certuserRepository.findById(certuserId).get();
        certstatus.setCertuser(certuser);
        Certificate certificate = certificateRepository.findById(certificateId).get();
        certstatus.setCertificate(certificate);


        certstatusRepository.save(certstatus);
    }

    public List<Certstatus> getCert(String certType){
        Optional<Certificate> cert = certificateRepository.findCertByType(certType);

        return certstatusRepository.findCertificatestatusByCertificate(cert.get());

    }


    public List<Certstatus> getUsersCert(String email){
        Optional<Certuser> certuser = certuserRepository.findByEmail(email);

        return certstatusRepository.findCertificatestatusByCertuser(certuser.get());

    }




   /*  public void addCertstatus(Certificatestatus certificatestatus){
          certificatestatusRepository.save(certificatestatus);
     }*/

/*     public void addCertuserToStatus(Long certificatestatusId, Long certuserId){
          Certuser certuser = certuserRepository.findById(certuserId).get();
          Certificatestatus certificatestatus = certificatestatusRepository.findById(certificatestatusId).get();
          certificatestatus.setCertuser(certuser);
          certificatestatusRepository.save(certificatestatus);
     }

     public void addCertToStatus(Long certificatestatusId, Long certificateId) {
          Certificate certificate = certificateRepository.findById(certificateId).get();
          Certificatestatus certificatestatus = certificatestatusRepository.findById(certificatestatusId).get();
          certificatestatus.setCertificate(certificate);
          certificatestatusRepository.save(certificatestatus);
     }*/
}
