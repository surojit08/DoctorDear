package com.surojit.doctordear.Hospital;

import com.surojit.doctordear.Hospital.DTO.RegisterRequestDTO;
import com.surojit.doctordear.Hospital.DTO.RegisterResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @PostMapping("")
    public RegisterResponseDTO registerHospital(@RequestBody RegisterRequestDTO registerRequestDTO) {
        Hospital hospital = hospitalService.registerHospitalWithCenters(registerRequestDTO.getHospital(), List.of(registerRequestDTO.getCenters()));
        return new RegisterResponseDTO(new RegisterResponseDTO.HospitalResponse(hospital.getId(), hospital.getName(), hospital.getAddress()));
    }


}
