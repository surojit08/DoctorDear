package com.surojit.doctordear.Hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterResponseDTO {
    private HospitalResponse hospital;


    @AllArgsConstructor
    @Getter
    @Setter
    public static class HospitalResponse {
        private Long id;
        private String name;
        private String address;

    }
}




