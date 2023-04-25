package com.surojit.doctordear.DoctorDepartment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.surojit.doctordear.Center.Center;
import com.surojit.doctordear.Center.CenterRepository;
import com.surojit.doctordear.Department.Department;
import com.surojit.doctordear.Department.DepartmentRepository;
import com.surojit.doctordear.DepartmentSchedule.DoctorSchedule;
import com.surojit.doctordear.DepartmentSchedule.QDoctorSchedule;
import com.surojit.doctordear.DepartmentSchedule.ScheduleDay;
import com.surojit.doctordear.Doctor.Doctor;
import com.surojit.doctordear.Doctor.DoctorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorDepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final CenterRepository centerRepository;
    private final DoctorDepartmentRepository doctorDepartmentRepository;
    @PersistenceContext
    EntityManager em;

    public DoctorDepartmentService(DepartmentRepository departmentRepository, DoctorRepository doctorRepository, CenterRepository centerRepository, DoctorDepartmentRepository doctorDepartmentRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
        this.centerRepository = centerRepository;
        this.doctorDepartmentRepository = doctorDepartmentRepository;
    }

    @Transactional
    public DoctorDepartment addDoctorToDepartment(Long doctorId, Long departmentId, Long centerId, List<DoctorSchedule> schedules) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId)
                                                    .orElseThrow(IllegalAccessException::new);
        Center center = centerRepository.findById(centerId)
                                        .orElseThrow(IllegalAccessException::new);
        Doctor doctor = doctorRepository.findById(doctorId)
                                        .orElseThrow(IllegalAccessException::new);
        var doctorDepartment = DoctorDepartment.builder()
                                               .department(department)
                                               .doctor(doctor)
                                               .status(DoctorDepartmentStatus.A)
                                               .build();

        if (schedules != null) {
            schedules.forEach(d -> {
                d.setDoctorDepartment(doctorDepartment);
                d.setCenter(center);
            });

            doctorDepartment.setDoctorSchedules(schedules);
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
        QDoctorSchedule departmentSchedule = QDoctorSchedule.doctorSchedule;
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        List<ScheduleTime> scheduleTimes = jpaQueryFactory.selectFrom(departmentSchedule)
                                                          .select(departmentSchedule.id, departmentSchedule.scheduleDay, departmentSchedule.time_from, departmentSchedule.time_to, departmentSchedule.center.id)
                                                          .where(departmentSchedule.doctorDepartment.id.eq(docDepId))
                                                          .fetch()
                                                          .stream()
                                                          .map(c -> new ScheduleTime(c.get(departmentSchedule.id), c.get(departmentSchedule.scheduleDay),
                                                                  c.get(departmentSchedule.time_from), c.get(departmentSchedule.time_to),
                                                                  c.get(departmentSchedule.center.id)))
                                                          .toList();
        System.out.println("Getting the results" + scheduleTimes);
        return scheduleTimes;
    }

    public record ScheduleTime(Long id, ScheduleDay scheduleDay, Long timeFrom, Long timeTo, Long centerId) {
    }


}
