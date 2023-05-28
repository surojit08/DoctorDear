package com.surojit.doctordear.Hospital;

import com.surojit.doctordear.Center.Center;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {


    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    /**
     * Register a new hospital to the system with List of centers.
     */
    public Hospital registerHospitalWithCenters(Hospital hospital, List<Center> centers) {
        centers.forEach(center -> center.setHospital(hospital));
        hospital.setCenters(centers);
        return hospitalRepository.save(hospital);
    }
}
