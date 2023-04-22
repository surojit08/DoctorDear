package com.surojit.doctordear.doctor_qualification;


import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorQualificationService {

    @Autowired
    DoctorQualificationRepository doctorQualificationRepository;

    @Autowired
    DoctorRepository doctorRepository;

    public List<DoctorQualification> addQualifications(Long doctorId, List<DoctorQualification> qualifications) {
        Doctor doc = doctorRepository.getReferenceById(doctorId);
        qualifications.forEach(qualification -> qualification.setDoctor(doc));
        doctorQualificationRepository.saveAll(qualifications);
        return qualifications;
    }


}
