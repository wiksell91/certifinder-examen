package com.example.certifinderexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Orderreq {

    @Id
    @SequenceGenerator(
            name = "orderreq_sequence",
            sequenceName = "orderreq_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "orderreq_sequence"
    )
    private Long id;



    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    public Certuser person;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER )
    @JoinColumn(name = "company_id")
    public Certuser company;

    private Orderstatus orderstatus;
    private String ordertype;
    private String comment;
    private LocalDate orderdate;






}
