package com.surojit.doctordear.Hospital;

import com.surojit.doctordear.Center.Center;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    @PostMapping("")
    public Hospital registerHospital(@RequestBody RegisterPayload regPay) {
        return hospitalService.registerHospital(regPay.hospital, List.of(regPay.centers));
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    private static class RegisterPayload {
        private Hospital hospital;
        private Center[] centers;
    }


}
