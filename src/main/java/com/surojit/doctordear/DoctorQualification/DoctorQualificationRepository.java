package com.surojit.doctordear.DoctorQualification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorQualificationRepository extends JpaRepository<DoctorQualification, Long> {
}
