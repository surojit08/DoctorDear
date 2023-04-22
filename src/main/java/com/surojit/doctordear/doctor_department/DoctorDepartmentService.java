package com.surojit.doctordear.doctor_department;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surojit.doctordear.DepartmentSchedule.DepartmentSchedule;
import com.surojit.doctordear.DepartmentSchedule.QDepartmentSchedule;
import com.surojit.doctordear.DepartmentSchedule.ScheduleDay;
import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.department.Department;
import com.surojit.doctordear.department.DepartmentRepository;
import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorDepartmentService {

    @PersistenceContext
    EntityManager em;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final CenterRepository centerRepository;
    private final DoctorDepartmentRepository doctorDepartmentRepository;

    public DoctorDepartmentService(DepartmentRepository departmentRepository, DoctorRepository doctorRepository, CenterRepository centerRepository, DoctorDepartmentRepository doctorDepartmentRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.centerRepository = centerRepository;
        this.doctorDepartmentRepository = doctorDepartmentRepository;
    }

    @Transactional
    public DoctorDepartment addDoctorToDepartment(Long doctorId, Long departmentId, Long centerId, List<DepartmentSchedule> schedules) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId)
                                                    .orElseThrow(IllegalAccessException::new);
        Center center = centerRepository.findById(centerId)
                                        .orElseThrow(IllegalAccessException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(IllegalAccessException::new);
        var doctorDepartment = DoctorDepartment.builder()
                                               .center(center)
                                               .department(department)
                                               .doctor(doctor)
                                               .status(DoctorDepartmentStatus.A)
                                               .build();

        if (schedules != null) {
            schedules.forEach(d -> {
                d.setDoctorDepartment(doctorDepartment);
                d.setDoctor(doctor);
            });

            doctorDepartment.setDepartmentSchedules(schedules);
        }

        return doctorDepartmentRepository.save(doctorDepartment);
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

    public List<ScheduleTime> findScheduleByDoctorDepartment(Long docDepId) {
        QDepartmentSchedule departmentSchedule = QDepartmentSchedule.departmentSchedule;
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<ScheduleTime> scheduleTimes = jpaQueryFactory.selectFrom(departmentSchedule)
                                                          .select(departmentSchedule.id, departmentSchedule.scheduleDay, departmentSchedule.time_from, departmentSchedule.time_to)
                                                          .where(departmentSchedule.doctorDepartment.id.eq(docDepId))
                                                          .fetch()
                                                          .stream()
                                                          .map(c -> new ScheduleTime(c.get(departmentSchedule.id), c.get(departmentSchedule.scheduleDay), c.get(departmentSchedule.time_from), c.get(departmentSchedule.time_to)))
                                                          .toList();
        System.out.println("Getting the results" + scheduleTimes);
        return scheduleTimes;
    }

    public record ScheduleTime(Long id, ScheduleDay scheduleDay, Long time_from, Long time_to) {
    }


}
