package com.surojit.doctordear.doctor_department;

import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.department.Department;
import com.surojit.doctordear.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne()
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne()
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne()
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

}
