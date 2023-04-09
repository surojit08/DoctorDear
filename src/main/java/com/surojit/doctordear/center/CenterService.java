package com.surojit.doctordear.center;

import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {
    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    public Center registerCenter(Center center, Long hospitalId) {
        Hospital hospital = hospitalRepository.getReferenceById(hospitalId);
        center.setHospital(hospital);
        return centerRepository.save(center);
    }

    public List<Center> findCenters(String centerName) {
        return centerRepository.findActiveCenters(centerName);
    }
}
