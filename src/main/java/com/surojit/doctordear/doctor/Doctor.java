package com.surojit.doctordear.doctor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Doctor {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false)
    private String firstName;

    @Column(length = 300, nullable = false)
    private String lastName;

    @Column()
    private String middleName;

    @Column(unique = false)
    private String regId; // doctor registration id.

    @JsonManagedReference
    @OneToMany(targetEntity = DoctorQualification.class, mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DoctorQualification> qualifications;

    @Column(nullable = false)
    private String status;


}
