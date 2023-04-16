package com.surojit.doctordear.center;

import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalRepository;
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
        var t = centerRepository.save(center);
        return centerRepository.findById(t.getId())
                               .get();
    }

    public List<Center> findCenters(String centerName) {
        return centerRepository.findActiveCenters(centerName);
    }
}
