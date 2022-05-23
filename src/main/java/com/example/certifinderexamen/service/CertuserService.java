package com.example.certifinderexamen.service;

import com.example.certifinderexamen.exception.BadRequestException;
import com.example.certifinderexamen.exception.ResourceNotFoundException;
import com.example.certifinderexamen.model.Authorities;
import com.example.certifinderexamen.model.Certuser;
import com.example.certifinderexamen.model.Orderreq;
import com.example.certifinderexamen.repository.AuthorityRepository;
import com.example.certifinderexamen.repository.CertuserRepository;
import com.example.certifinderexamen.repository.OrderreqRepository;
import com.example.certifinderexamen.util.CustomPasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@AllArgsConstructor
@Service
public class CertuserService {


    private final CertuserRepository certuserRepository;
    private final AuthorityRepository authorityRepository;
    private final OrderreqRepository orderreqRepository;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    public Certuser getUserByUserName(String username){
        return certuserRepository.findCertuserByUsername(username);
    }

    public List<Certuser> getAllUsers(){
        return certuserRepository.findAll();
    }

    public void addUser(Certuser certuser){
        Boolean existsUser = certuserRepository
                .selectExistsUser(certuser.getUsername());

        if (existsUser){
            throw new BadRequestException(
                    "Användaren " + certuser.getFullName() + " finns redan i systemet"
            );
        }

        Authorities authority = new Authorities();
        authority.setAuthority("ROLE_USER");
        authority.setCertuser(certuser);
        String encodedPassword = customPasswordEncoder.getPasswordEncoder().encode(certuser.getPassword());
        certuser.setPassword(encodedPassword);
        certuserRepository.save(certuser);
        authorityRepository.save(authority);

    }

    public void addCompany(Certuser certuser){
        Boolean existsCompany = certuserRepository
                .selectExistsUser(certuser.getUsername());

        if (existsCompany){
            throw new BadRequestException(
                    "Företaget " + certuser.getFullName() + " finns redan i systemet"
            );
        }
        Authorities authority = new Authorities();
        authority.setAuthority("ROLE_COMPANY");
        authority.setCertuser(certuser);
        String encodedPassword = customPasswordEncoder.getPasswordEncoder().encode(certuser.getPassword());
        certuser.setPassword(encodedPassword);
        certuserRepository.save(certuser);
        authorityRepository.save(authority);
    }

    public Certuser updateUser(Certuser certuser, Map<String, Object> fields) throws ResourceNotFoundException, ParseException {
        fields.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Certuser.class, (String) key);
            field.setAccessible(true);
            ReflectionUtils.setField(field, certuser, value);
        });

        return certuserRepository.save(certuser);
    }


    public void deleteUser(String username){
        Certuser user = certuserRepository.findCertuserByUsername(username);
        List<Orderreq> order = orderreqRepository.findOrderreqByUser(username);
        List<Authorities> authorities = authorityRepository.findAllByUserId(user.getId());
        orderreqRepository.deleteAll(order);
        authorityRepository.deleteAll(authorities);
        certuserRepository.deleteById(user.getId());

    }



}
