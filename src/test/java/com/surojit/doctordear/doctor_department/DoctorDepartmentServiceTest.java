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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DoctorDepartmentServiceTest {


    Department department;
    Doctor doctor;

    Center center;

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

    @BeforeEach
    void setUp() {
        // create a hospital and a center with it
        Hospital newHospital = Hospital.builder().name("Alpaca").address("Barack pore").build();
        var hospital = hospitalRepository.save(newHospital);

        // add a center to the hospital
        Center newCenter = Center.builder().name("Center 1").code("122").hospital(hospital).status(CenterStatus.A).build();
        center = centerRepository.save(newCenter);

        // create department
        department = Department.builder().name("Dep 1").hospital(hospital).build();
        department = departmentRepository.save(department);

        // create a doctor.
        Doctor newDoctor = Doctor.builder().firstName("Puma").lastName("Bag").regId("344").status(DoctorStatus.A).build();
        doctor = doctorRepository.save(newDoctor);

    }


    @Test
//    @Transactional
    void addDoctorToDepartment() throws IllegalAccessException {
        System.out.println("here in test");
        DoctorDepartment doctorDepartment = doctorDepartmentService.addDoctorToDepartment(doctor.getId(), department.getId(), center.getId());

        assertEquals(doctorDepartment.getDepartment().getId(), department.getId());
        assertEquals(doctorDepartment.getDoctor().getId(), doctor.getId());
        assertEquals(doctorDepartment.getCenter().getId(), center.getId());
    }
}