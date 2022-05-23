package com.example.certifinderexamen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@ToString
@Entity
@Table
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Certstatus {

    @Id
    @SequenceGenerator(
            name = "certstatus_sequence",
            sequenceName = "certstatus_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "certstatus_sequence"
    )
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER )
    @JoinColumn(name = "certuser_id")
    public Certuser certuser;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "certificate_id")
    public Certificate certificate;

    private LocalDate validto;
    private String regnumber;
    private String generalinfo;

}
