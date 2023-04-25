package com.surojit.doctordear.DepartmentSchedule;

import com.surojit.doctordear.Center.Center;
import com.surojit.doctordear.DoctorDepartment.DoctorDepartment;
import jakarta.persistence.*;
import lombok.*;

// Department schedule contains Center id, Doctor id, Doctor Department id

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DoctorSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long time_from;

    @Column(nullable = false)
    private Long time_to;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "doctor_department_id")
    private DoctorDepartment doctorDepartment;

    @Enumerated(EnumType.STRING)
    private ScheduleDay scheduleDay;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false)
    @ToString.Exclude
    private Center center;


}
