package com.surojit.doctordear.doctor;

import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;


    /**
     * Get a general description of doctor such as name, registration number etc.
     */
    @GetMapping("/{doctorId}")
    public void getDoctorGeneralDescription(@PathVariable String doctorId) {

    }

    @GetMapping("/{doctorId}/center/{centerId}")
    public void getDoctorOfCenterDescription(@PathVariable String centerId, @PathVariable String doctorId) {

    }

    // doctor self register to system.
    @PostMapping("/register")
    public Doctor registerDoctorToSystem(@RequestBody RegisterPayload regPay) {
        System.out.println("boo =>" + regPay);

        Doctor doc = regPay.doctor;
        ArrayList<DoctorQualification> doctorQualifications = new ArrayList<>();
        if (regPay.qualifications.length > 0) {
            QualificationReq[] qlFs = regPay.qualifications;
            for (var qlf : qlFs) {
                doctorQualifications.add(DoctorQualification.builder().name(qlf.name).year(qlf.year).place(qlf.place).build());
            }
        }


        return doctorService.registerDoctorToSystem(doc,doctorQualifications);
    }

    @PostMapping("/register/{centerId}")
    public void registerDoctorByCenter(@RequestBody RegisterPayload regPay, @PathVariable String centerId) {


    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    static class QualificationReq {
        private String name;
        private Integer year;
        private String place;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    static class RegisterPayload {
        private Doctor doctor;
        private QualificationReq[] qualifications;
    }
}
