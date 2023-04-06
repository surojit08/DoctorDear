package com.surojit.doctordear.doctor;

import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    public Doctor registerDoctorToSystem(Doctor doc, List<DoctorQualification> doctorQualifications){
        doctorQualifications.forEach(ql -> ql.setDoctor(doc));
        doc.setQualifications(doctorQualifications);
        return doctorRepository.save(doc);
    }


}


