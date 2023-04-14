package com.surojit.doctordear.department;

import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CenterRepository centerRepository;

    Department addNewDepartment(Department department, Long centerId) throws IllegalAccessException {
        System.out.println("center id" + centerId);
        // find the center id;
        Optional<Center> center = centerRepository.findById(centerId);
        if (center.isPresent()) {
            department.setCenter(center.get());
            return departmentRepository.save(department);
        } else {
            throw new IllegalAccessException();
        }
    }
}
