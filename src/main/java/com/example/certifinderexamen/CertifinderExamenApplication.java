package com.example.certifinderexamen;

import com.example.certifinderexamen.model.Certificate;
import com.example.certifinderexamen.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

import static com.example.certifinderexamen.model.Bransch.BYGG;
import static com.example.certifinderexamen.model.Bransch.FORDON;

@SpringBootApplication
public class CertifinderExamenApplication {

	@Autowired
	private CertificateRepository certificateRepository;

	public static void main(String[] args) {
		SpringApplication.run(CertifinderExamenApplication.class, args);
	}


	@PostConstruct
	protected void init(){
		Certificate cert1 = new Certificate("Het arbete", BYGG);
		certificateRepository.save(cert1);

		Certificate cert2 = new Certificate("Truck", FORDON);
		certificateRepository.save(cert2);

		Certificate cert3 = new Certificate("Säkra lyft", BYGG);
		certificateRepository.save(cert3);

		Certificate cert4 = new Certificate("BAS-U", BYGG);
		certificateRepository.save(cert4);

		Certificate cert5 = new Certificate("BAS-P", BYGG);
		certificateRepository.save(cert5);

		Certificate cert6 = new Certificate("Skyddsombud", BYGG);
		certificateRepository.save(cert6);

		Certificate cert7 = new Certificate("Lyftkran", FORDON);
		certificateRepository.save(cert7);

		Certificate cert8 = new Certificate("Lastbil", FORDON);
		certificateRepository.save(cert8);

		Certificate cert9 = new Certificate("Asbest", BYGG);
		certificateRepository.save(cert9);

		Certificate cert10 = new Certificate("Besiktning", BYGG);
		certificateRepository.save(cert10);


	}
}
