package com.surojit.doctordear.doctor;


import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import com.surojit.doctordear.doctor_qualification.DoctorQualificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorQualificationRepository doctorQualificationRepository;


    public Doctor registerDoctorToSystem(Doctor doc, List<DoctorQualification> doctorQualifications) {
        doctorQualifications.forEach(ql -> ql.setDoctor(doc));
        doc.setQualifications(doctorQualifications);
        return doctorRepository.save(doc);
    }


    public Doctor getDoctorGeneralDescription(Long doctorId) throws IllegalAccessException {
        return doctorRepository.findById(doctorId)
                               .orElseThrow(IllegalAccessException::new);
    }


}


