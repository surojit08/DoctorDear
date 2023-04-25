package com.surojit.doctordear.Department;

import com.surojit.doctordear.Hospital.Hospital;
import com.surojit.doctordear.Hospital.HospitalRepository;
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
        // find the Hospital;
        Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
        if (hospital.isPresent()) {
            department.setHospital(hospital.get());
            return departmentRepository.save(department);
        } else {
            throw new IllegalAccessException();
        }
    }
}
