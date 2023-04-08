package com.surojit.doctordear.center;

import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CenterServiceTest {
    @Autowired
    CenterService centerService;

    @Autowired
    HospitalService hospitalService;

    @Test
    void createCenter() {

        // create hospital
        Hospital newHospital = Hospital.builder().name("L.Asm").address("Kolkata").build();
        Hospital saved_hospital = hospitalService.registerHospital(newHospital, List.of());

        // create new center of the hospital.
        Center newCenter = Center.builder().name("armin").code("111").address("kolkata").hospital(saved_hospital)
                .contact(CenterContact.builder().primary_email("122222").primary_phone("2323").build()).build();
        Center center = centerService.registerCenter(newCenter);

        assertEquals(center.getId(), 1);
        assertEquals(center.getName(), "armin");
        assertEquals(center.getCode(), "111");
        assertEquals(center.getAddress(), "kolkata");
        assertEquals(center.getContact().getPrimary_email(), "122222");
        assertEquals(center.getContact().getPrimary_phone(), "2323");

        assertEquals(center.getHospital().getName(), "L.Asm");
    }
}