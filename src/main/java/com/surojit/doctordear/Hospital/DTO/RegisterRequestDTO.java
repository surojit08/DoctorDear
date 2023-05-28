package com.surojit.doctordear.Hospital.DTO;


import com.surojit.doctordear.Center.Center;
import com.surojit.doctordear.Hospital.Hospital;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private Hospital hospital;
    private Center[] centers;
}

