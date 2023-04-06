package com.surojit.doctordear.doctor;

import com.surojit.doctordear.doctor.projections.DoctorInfo;
import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DoctorServiceTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    void registerDoctorToSystem() {

        // create a new doctor
        Doctor doc  = Doctor.builder().firstName("Soumen").lastName("Das").regId("123RE123").build();

        DoctorQualification q1 = DoctorQualification.builder().name("MD").year(2003).build();
        DoctorQualification q2 = DoctorQualification.builder().name("M.B.B.S").year(2001).build();
        q1.setDoctor(doc);
        q2.setDoctor(doc);
        List<DoctorQualification> qualifications = List.of(q1);
        doc.setQualifications(qualifications);

        var saved_doc = doctorRepository.save(doc);
        assertEquals(saved_doc.getFirstName(),"Soumen");

        DoctorInfo doctorInfo = doctorRepository.getDoctorById(saved_doc.getId());
        assertEquals(doctorInfo.getFullName(), "Soumen Das");
    }

}