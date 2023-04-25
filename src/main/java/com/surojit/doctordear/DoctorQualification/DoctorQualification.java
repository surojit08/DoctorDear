package com.surojit.doctordear.DoctorQualification;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.surojit.doctordear.Doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor_qualification")
public class DoctorQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Doctor doctor;

    @Column(nullable = false)
    private String name;

    private Integer year;

    private String place;
}
