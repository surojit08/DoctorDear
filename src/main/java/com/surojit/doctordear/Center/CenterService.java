package com.surojit.doctordear.Center;

import com.surojit.doctordear.Hospital.Hospital;
import com.surojit.doctordear.Hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {
    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    private CenterRepository centerRepository;

    public Center registerCenter(Center center, Long hospitalId) {
        Hospital hospital = hospitalRepository.getReferenceById(hospitalId);
        center.setHospital(hospital);

        return centerRepository.save(center);
    }

    public List<Center> findCenters(String centerName) {
        return centerRepository.findActiveCenters(centerName);
    }
}
