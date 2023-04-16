package com.surojit.doctordear.doctor_department;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.department.Department;
import com.surojit.doctordear.department.DepartmentRepository;
import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private CenterRepository centerRepository;
    @Autowired
    private DoctorDepartmentRepository doctorDepartmentRepository;

    @Transactional
    public DoctorDepartment addDoctorToDepartment(Long doctorId, Long departmentId, Long centerId, Schedule schedule) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(IllegalAccessException::new);
        Center center = centerRepository.findById(centerId).orElseThrow(IllegalAccessException::new);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(IllegalAccessException::new);
        var doctorDepartmentBuilder = DoctorDepartment.builder().center(center)
                .department(department).doctor(doctor).status(DoctorDepartmentStatus.A);

        if (schedule != null) {
            doctorDepartmentBuilder.schedule(schedule);
        }

        return doctorDepartmentRepository.save(doctorDepartmentBuilder.build());
    }


    public DoctorDepartment findDoctorOfDepartment(Long doctorId, Long depId) throws IllegalAccessException {
        // we use query dsl here
        QDoctorDepartment qDoctorDepartment = QDoctorDepartment.doctorDepartment;
        BooleanExpression ofDepartment = qDoctorDepartment.department.id.eq(depId);
        BooleanExpression onlyActive = qDoctorDepartment.status.eq(DoctorDepartmentStatus.A);
        BooleanExpression ofDoctor = qDoctorDepartment.doctor.id.eq(doctorId);

        return doctorDepartmentRepository.findOne(onlyActive.and(ofDepartment).and(ofDoctor)).orElseThrow(IllegalAccessException::new);
    }


}
