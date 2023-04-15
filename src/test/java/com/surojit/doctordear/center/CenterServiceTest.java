package com.surojit.doctordear.center;

import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CenterServiceTest {
    @Autowired
    CenterService centerService;

    @Autowired
    HospitalService hospitalService;

    @Test
    @Order(1)
    void createCenter() {

        // create a hospital
        Hospital newHospital = Hospital.builder().name("L.Asm").address("Kolkata").build();
        Hospital saved_hospital = hospitalService.registerHospital(newHospital, List.of());

        // create a new center of the hospital.
        Center newCenter = Center.builder().name("armin").code("111").address("kolkata").hospital(saved_hospital).status(CenterStatus.A)
                .contact(CenterContact.builder().primary_email("122222").primary_phone("2323").build()).build();
        Center center = centerService.registerCenter(newCenter, newHospital.getId());

        assertEquals(center.getName(), "armin");
        assertEquals(center.getCode(), "111");
        assertEquals(center.getAddress(), "kolkata");
        assertEquals(center.getContact().getPrimary_email(), "122222");
        assertEquals(center.getContact().getPrimary_phone(), "2323");
    }

    @Test
    @Order(2)
    void getActiveCenters() {
        List<Center> centers = centerService.findCenters("%arm%");
        assertFalse(centers.isEmpty());
    }
}