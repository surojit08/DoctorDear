package com.surojit.doctordear.Doctor.projections;

import org.springframework.beans.factory.annotation.Value;

public interface DoctorInfo {
    String getFirstName();

    String getLastName();

    String getMiddleName();

    @Value("#{target.firstName+' '+target.lastName}")
    String getFullName();
}
