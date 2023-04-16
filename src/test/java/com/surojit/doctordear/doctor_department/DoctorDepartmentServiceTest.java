package com.surojit.doctordear.doctor_department;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Schedule schedule = Schedule.builder()
                                    .onSunday(true)
                                    .onFriday(true)
                                    .time_from(2L)
                                    .time_to(3L)
                                    .build();
        DoctorDepartment doctorDepartment = doctorDepartmentService.addDoctorToDepartment(doctor.getId(), department.getId(), center.getId(), schedule);

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
        assertEquals(doctorDepartment.getSchedule()
                                     .getOnFriday(), true);
        assertEquals(doctorDepartment.getSchedule()
                                     .getOnSunday(), true);
        assertEquals(false, doctorDepartment.getSchedule()
                                            .getOnMonday());
        assertEquals(false, doctorDepartment.getSchedule()
                                            .getOnSaturday());
        assertEquals(false, doctorDepartment.getSchedule()
                                            .getOnWednesday());
        assertEquals(false, doctorDepartment.getSchedule()
                                            .getOnThursday());
        assertEquals(2L, doctorDepartment.getSchedule()
                                         .getTime_from());
        assertEquals(3L, doctorDepartment.getSchedule()
                                         .getTime_to());


    }

    @Test
    @Order(3)
    void findScheduleByDoctorDepartment() throws IllegalAccessException {
        Schedule schedule = doctorDepartmentService.findScheduleByDoctorDepartment(docDepId);
        assertEquals(true, schedule.getOnFriday());
        assertEquals(true, schedule.getOnSunday());
        assertEquals(2L, schedule.getTime_from());
        assertEquals(3L, schedule.getTime_to());
    }

    @Test
    @Order(4)
    void findScheduleByDoctorDepartmentWithInvalidId() {
        assertThrows(IllegalAccessException.class, () -> {
            Schedule schedule = doctorDepartmentService.findScheduleByDoctorDepartment(55L);
        });
    }
}