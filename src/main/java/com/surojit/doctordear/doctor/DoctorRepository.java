package com.surojit.doctordear.doctor;

import com.surojit.doctordear.doctor.projections.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    DoctorInfo getDoctorById(Long id);

    Doctor getReferenceByIdAndStatus(Long id, String status);
}
