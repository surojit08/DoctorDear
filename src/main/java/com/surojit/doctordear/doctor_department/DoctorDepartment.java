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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    private Center center;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DoctorDepartmentStatus status = DoctorDepartmentStatus.A;

    @Embedded
    private Schedule schedule;


}

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class Schedule {
    @Column(nullable = false)
    private Long time_from;

    @Column(nullable = false)
    private Long time_to;

    @Builder.Default
    private Boolean onSunday = false;

    @Builder.Default
    private Boolean onMonday = false;

    @Builder.Default
    private Boolean onTuesday = false;

    @Builder.Default
    private Boolean onWednesday = false;

    @Builder.Default
    private Boolean onThursday = false;

    @Builder.Default
    private Boolean onFriday = false;

    @Builder.Default
    private Boolean onSaturday = false;
}