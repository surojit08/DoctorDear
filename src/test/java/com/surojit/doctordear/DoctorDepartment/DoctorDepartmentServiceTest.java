package com.surojit.doctordear.DoctorDepartment;

import com.surojit.doctordear.Center.Center;
import com.surojit.doctordear.Center.CenterRepository;
import com.surojit.doctordear.Center.CenterStatus;
import com.surojit.doctordear.Department.Department;
import com.surojit.doctordear.Department.DepartmentRepository;
import com.surojit.doctordear.DepartmentSchedule.DoctorSchedule;
import com.surojit.doctordear.DepartmentSchedule.ScheduleDay;
import com.surojit.doctordear.Doctor.Doctor;
import com.surojit.doctordear.Doctor.DoctorRepository;
import com.surojit.doctordear.Doctor.DoctorStatus;
import com.surojit.doctordear.Hospital.Hospital;
import com.surojit.doctordear.Hospital.HospitalRepository;
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
        // create a Hospital and a Center with it
        Hospital newHospital = Hospital.builder()
                                       .name("Alpaca")
                                       .address("Barack pore")
                                       .build();
        var hospital = hospitalRepository.save(newHospital);

        // add a Center to the Hospital
        Center newCenter = Center.builder()
                                 .name("Center 1")
                                 .code("122")
                                 .hospital(hospital)
                                 .status(CenterStatus.A)
                                 .build();
        center = centerRepository.save(newCenter);

        // create Department
        department = Department.builder()
                               .name("Dep 1")
                               .hospital(hospital)
                               .build();
        department = departmentRepository.save(department);
        depId = department.getId();

        // create a Doctor.
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
        DoctorSchedule schedule1 = DoctorSchedule.builder()
                                                 .scheduleDay(ScheduleDay.SUNDAY)
                                                 .time_from(4L)
                                                 .time_to(5L)
                                                 .build();
        DoctorDepartment doctorDepartment = doctorDepartmentService.addDoctorToDepartment(doctor.getId(), department.getId(), center.getId(), List.of(schedule1));

        assertEquals(doctorDepartment.getDepartment()
                                     .getId(), department.getId());
        assertEquals(doctorDepartment.getDoctor()
                                     .getId(), doctor.getId());
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
        assertEquals(firstSchedule.timeFrom(), 4L);
        assertEquals(firstSchedule.timeTo(), 5L);
        assertEquals(firstSchedule.scheduleDay(), ScheduleDay.SUNDAY);
        assertEquals(firstSchedule.centerId(), center.getId());
    }

    @Test
    @Order(4)
    void findScheduleByDoctorDepartmentWithInvalidId() {
        List<DoctorDepartmentService.ScheduleTime> schedules = doctorDepartmentService.findScheduleByDoctorDepartment(55L);
        assertTrue(schedules.isEmpty());
    }
}