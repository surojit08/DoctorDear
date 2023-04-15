package com.surojit.doctordear.department;

import com.surojit.doctordear.hospital.Hospital;
import com.surojit.doctordear.hospital.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    Department addNewDepartment(Department department, Long hospitalId) throws IllegalAccessException {
        // find the hospital;
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if (hospital.isPresent()) {
            department.setHospital(hospital.get());
            return departmentRepository.save(department);
        } else {
            throw new IllegalAccessException();
        }
    }
}
