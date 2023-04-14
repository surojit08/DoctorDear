package com.surojit.doctordear.hospital;

import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HospitalServiceTest {

    @Autowired
    HospitalService hospitalService;

    @Test
    void registerHospitalWithoutCenter() {
        Hospital newHospital = Hospital.builder().name("Alpaca").address("Barack pore").build();
        Hospital savedHospital = hospitalService.registerHospital(newHospital, List.of());
        assertEquals(savedHospital.getName(), "Alpaca");
    }

    @Test
    void registerHospitalWithCenter() {
        Hospital newHospital = Hospital.builder().name("Alpaca").address("Barack pore").build();
        Center center1 = Center.builder().code("111").hospital(newHospital).name("BBO").status(CenterStatus.A).build();
        Center center2 = Center.builder().code("222").hospital(newHospital).name("MPP").status(CenterStatus.A).build();
        Hospital savedHospital = hospitalService.registerHospital(newHospital, List.of(center1, center2));
        assertEquals(savedHospital.getName(), "Alpaca");
        assertEquals(savedHospital.getCenters().size(), 2);

        // first center
        Center firstCenter = savedHospital.getCenters().get(0);
        assertEquals(firstCenter.getId(), 1);
        assertEquals(firstCenter.getName(), "BBO");

        // second center
        Center secondCenter = savedHospital.getCenters().get(1);
        assertEquals(secondCenter.getName(), "MPP");
        assertEquals(secondCenter.getId(), 2);
    }
}