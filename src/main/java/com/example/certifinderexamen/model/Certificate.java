package com.example.certifinderexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Table
@Entity
public class Certificate {

    @Id
    @SequenceGenerator(
            name = "certificate_sequence",
            sequenceName = "certificate_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "certificate_sequence"
    )
    private Long id;
    private String certType;

    @Enumerated(EnumType.STRING)
    private Bransch bransch;

    @JsonIgnore
    @OneToMany(
            mappedBy = "certificate",
            cascade = CascadeType.MERGE,fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Certstatus> certstats = new ArrayList<>();


    public Certificate() {
    }

    public Certificate(String certType, Bransch bransch) {
        this.certType = certType;
        this.bransch = bransch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public Bransch getBransch() {
        return bransch;
    }

    public void setBransch(Bransch bransch) {
        this.bransch = bransch;
    }

    public void setCertificatestats(List<Certstatus> certificatestats) {
        this.certstats = certstats;
    }
}
