package com.surojit.doctordear.doctor_department;

import com.surojit.doctordear.center.Center;
import com.surojit.doctordear.center.CenterRepository;
import com.surojit.doctordear.department.Department;
import com.surojit.doctordear.department.DepartmentRepository;
import com.surojit.doctordear.doctor.Doctor;
import com.surojit.doctordear.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorDepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private CenterRepository centerRepository;
    @Autowired
    private DoctorDepartmentRepository doctorDepartmentRepository;

    //    @Transactional
    public DoctorDepartment addDoctorToDepartment(Long doctorId, Long departmentId, Long centerId) throws IllegalAccessException {
        Department department = departmentRepository.findById(departmentId).orElseThrow(IllegalAccessException::new);
        Center center = centerRepository.findById(centerId).orElseThrow(IllegalAccessException::new);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(IllegalAccessException::new);
        System.out.println("in service");
        DoctorDepartment doctorDepartment = DoctorDepartment.builder().center(center).department(department).doctor(doctor).build();
        return doctorDepartmentRepository.save(doctorDepartment);
    }


}
