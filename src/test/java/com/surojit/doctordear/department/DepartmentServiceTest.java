package com.surojit.doctordear.department;

import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.center.CenterStatus;
import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @BeforeAll
    void setUp() {
        // create a hospital and a center with it
        Hospital newHospital = Hospital.builder().name("Alpaca").address("Barack pore").build();
        hospital = hospitalRepository.save(newHospital);

        Center newCenter = Center.builder().code("111").hospital(newHospital).name("BBO").status(CenterStatus.A).build();
        center = centerRepository.save(newCenter);


    }


    @Test
    void addNewDepartment() throws IllegalAccessException {
        Department newDepartment = Department.builder().name("ENT").build();
        Department savedDepartment = departmentService.addNewDepartment(newDepartment, center.getId());
        assertEquals(savedDepartment.getName(), "ENT");
        assertEquals(savedDepartment.getCenter().getName(), "BBO");
    }

    @Test
    void addNewDepartmentWithInvalidCenter() throws IllegalAccessException {
        Department newDepartment = Department.builder().name("ENT").build();
        assertThrows(IllegalAccessException.class, () -> {
            departmentService.addNewDepartment(newDepartment, 2L);
        });
    }
}