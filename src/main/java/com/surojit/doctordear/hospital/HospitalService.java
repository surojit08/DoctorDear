package com.surojit.doctordear.hospital;

import com.surojit.doctordear.center.Center;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    public Hospital registerHospital(Hospital hospital, List<Center> centers) {
        centers.forEach(c -> c.setHospital(hospital));
        hospital.setCenters(centers);
        return hospitalRepository.save(hospital);
    }
}
