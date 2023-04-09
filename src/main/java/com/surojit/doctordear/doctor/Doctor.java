package com.surojit.doctordear.doctor;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

enum DoctorStatus {
    A, C, S
}

@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
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
    @ToString.Exclude
    private List<DoctorQualification> qualifications;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DoctorStatus status = DoctorStatus.A;


}