package com.surojit.doctordear.doctor_department;

import com.surojit.doctordear.DepartmentSchedule.DepartmentSchedule;
import com.surojit.doctordear.DepartmentSchedule.ScheduleDay;
import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.center.CenterStatus;
import com.surojit.doctordear.department.Department;
import com.surojit.doctordear.department.DepartmentRepository;
import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor.DoctorRepository;
import com.surojit.doctordear.doctor.DoctorStatus;
import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoctorDepartmentServiceTest {

    Department department;
    Doctor doctor;

    Center center;

    Long docDepId;
    Long docId;
    Long depId;

    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private CenterRepository centerRepository;


    @Autowired
    private DoctorDepartmentService doctorDepartmentService;

    @BeforeAll
    void setUp() {
        // create a hospital and a center with it
        Hospital newHospital = Hospital.builder()
                                       .name("Alpaca")
                                       .address("Barack pore")
                                       .build();
        var hospital = hospitalRepository.save(newHospital);

        // add a center to the hospital
        Center newCenter = Center.builder()
                                 .name("Center 1")
                                 .code("122")
                                 .hospital(hospital)
                                 .status(CenterStatus.A)
                                 .build();
        center = centerRepository.save(newCenter);

        // create department
        department = Department.builder()
                               .name("Dep 1")
                               .hospital(hospital)
                               .build();
        department = departmentRepository.save(department);
        depId = department.getId();

        // create a doctor.
        Doctor newDoctor = Doctor.builder()
                                 .firstName("Puma")
                                 .lastName("Bag")
                                 .regId("344")
                                 .status(DoctorStatus.A)
                                 .build();
        doctor = doctorRepository.save(newDoctor);
        docId = doctor.getId();

    }


    @Test
    @Order(1)
    void addDoctorToDepartment() throws IllegalAccessException {
        DepartmentSchedule schedule1 = DepartmentSchedule.builder()
                                                         .scheduleDay(ScheduleDay.SUNDAY)
                                                         .time_from(4L)
                                                         .time_to(5L)
                                                         .build();
        DoctorDepartment doctorDepartment = doctorDepartmentService.addDoctorToDepartment(doctor.getId(), department.getId(), center.getId(), List.of(schedule1));

        assertEquals(doctorDepartment.getDepartment()
                                     .getId(), department.getId());
        assertEquals(doctorDepartment.getDoctor()
                                     .getId(), doctor.getId());
        assertEquals(doctorDepartment.getCenter()
                                     .getId(), center.getId());
        docDepId = doctorDepartment.getId();
    }

    @Test
    @Order(2)
    void findDoctorOfDepartment() throws IllegalAccessException {
        DoctorDepartment doctorDepartment = doctorDepartmentService.findDoctorOfDepartment(docId, depId);
        assertEquals(doctorDepartment.getId(), docDepId);


    }

    @Test
    @Order(3)
    void findScheduleByDoctorDepartment() {
        List<DoctorDepartmentService.ScheduleTime> scheduleTimes = doctorDepartmentService.findScheduleByDoctorDepartment(docDepId);
        assertTrue(scheduleTimes.size() > 0);
        DoctorDepartmentService.ScheduleTime firstSchedule = scheduleTimes.get(0);
        assertEquals(firstSchedule.time_from(), 4L);
        assertEquals(firstSchedule.time_to(), 5L);
        assertEquals(firstSchedule.scheduleDay(), ScheduleDay.SUNDAY);
    }

    @Test
    @Order(4)
    void findScheduleByDoctorDepartmentWithInvalidId() {
        List<DoctorDepartmentService.ScheduleTime> schedules = doctorDepartmentService.findScheduleByDoctorDepartment(55L);
        assertTrue(schedules.isEmpty());
    }
}