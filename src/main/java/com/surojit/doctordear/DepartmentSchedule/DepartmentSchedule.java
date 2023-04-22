package com.surojit.doctordear.DepartmentSchedule;

import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor_department.DoctorDepartment;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DepartmentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long time_from;

    @Column(nullable = false)
    private Long time_to;

    // a department schedule must be unique for a doctor.
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "doctor_department_id")
    private DoctorDepartment doctorDepartment;

    @Enumerated(EnumType.STRING)
    private ScheduleDay scheduleDay;


    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


}
