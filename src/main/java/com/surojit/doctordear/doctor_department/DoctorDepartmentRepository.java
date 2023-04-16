package com.surojit.doctordear.doctor_department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface DoctorDepartmentRepository extends JpaRepository<DoctorDepartment, Long>, QuerydslPredicateExecutor<DoctorDepartment> {
}
