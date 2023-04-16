package com.surojit.doctordear.doctor_department;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.department.Department;
import com.surojit.doctordear.department.DepartmentRepository;
import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorDepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @PersistenceContext
    EntityManager em;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private CenterRepository centerRepository;
    @Autowired
    private DoctorDepartmentRepository doctorDepartmentRepository;

    @Transactional
    public DoctorDepartment addDoctorToDepartment(Long doctorId, Long departmentId, Long centerId, Schedule schedule) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId)
                                                    .orElseThrow(IllegalAccessException::new);
        Center center = centerRepository.findById(centerId)
                                        .orElseThrow(IllegalAccessException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(IllegalAccessException::new);
        var doctorDepartmentBuilder = DoctorDepartment.builder()
                                                      .center(center)
                                                      .department(department)
                                                      .doctor(doctor)
                                                      .status(DoctorDepartmentStatus.A);

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

        return doctorDepartmentRepository.findOne(onlyActive.and(ofDepartment)
                                                            .and(ofDoctor))
                                         .orElseThrow(IllegalAccessException::new);
    }


    public Schedule findScheduleByDoctorDepartment(Long docDepId) throws IllegalAccessException {
        QDoctorDepartment doctorDepartment = QDoctorDepartment.doctorDepartment;
        JPAQuery<?> jpaQuery = new JPAQuery<>(em);
        Optional<Schedule> scheduleOp = Optional.ofNullable(jpaQuery.from(doctorDepartment)
                                                                    .where(doctorDepartment.id.eq(docDepId))
                                                                    .select(doctorDepartment.schedule)
                                                                    .fetchFirst());
        return scheduleOp.orElseThrow(IllegalAccessException::new);
    }


}
