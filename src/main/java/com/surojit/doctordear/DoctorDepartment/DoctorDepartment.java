package com.surojit.doctordear.DoctorDepartment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.surojit.doctordear.Department.Department;
import com.surojit.doctordear.DepartmentSchedule.DoctorSchedule;
import com.surojit.doctordear.Doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "doctor_department")
public class DoctorDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @ToString.Exclude
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @ToString.Exclude
    private Department department;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @ToString.Exclude
    private DoctorDepartmentStatus status = DoctorDepartmentStatus.A;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(targetEntity = DoctorSchedule.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "doctorDepartment")
    private List<DoctorSchedule> doctorSchedules;


}

