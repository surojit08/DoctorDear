package com.surojit.doctordear.department;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.surojit.doctordear.hospital.Hospital;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "department")
@ToString
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Hospital hospital;
}
