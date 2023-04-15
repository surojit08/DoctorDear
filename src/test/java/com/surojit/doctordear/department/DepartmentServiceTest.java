package com.surojit.doctordear.department;

import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.center.CenterStatus;
import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartmentServiceTest {


    Hospital hospital;
    Center center;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CenterRepository centerRepository;

    @BeforeEach
    @Order(0)
    void setUp() {
        // create a hospital and a center with it
        Hospital newHospital = Hospital.builder().name("Alpaca").address("Barack pore").build();
        hospital = hospitalRepository.save(newHospital);

        Center newCenter = Center.builder().code("111").hospital(newHospital).name("BBO").status(CenterStatus.A).build();
        center = centerRepository.save(newCenter);


    }


    @Test
    @Order(1)
    void addNewDepartment() throws IllegalAccessException {
        System.out.println(center);
        Department newDepartment = Department.builder().name("ENT").build();
        Department savedDepartment = departmentService.addNewDepartment(newDepartment, center.getId());
        assertEquals(savedDepartment.getName(), "ENT");
        assertEquals(savedDepartment.getCenter().getName(), "BBO");
    }


    @Test
    @Order(2)
    void addNewDepartmentWithInvalidCenter() {
        Department newDepartment = Department.builder().name("ENT").build();
        System.out.println(newDepartment);
        assertThrows(IllegalAccessException.class, () -> departmentService.addNewDepartment(newDepartment, 122L));
    }
}