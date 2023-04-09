package com.surojit.doctordear.doctor;

import com.surojit.doctordear.doctor_qualification.DoctorQualification;
import com.surojit.doctordear.doctor_qualification.DoctorQualificationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @Autowired
    DoctorQualificationService doctorQualificationService;


    /**
     * Get a general description of doctor such as name, registration number etc.
     */
    @GetMapping("/{doctorId}")
    public Doctor getDoctorGeneralDescription(@PathVariable Long doctorId) {
        return doctorService.getDoctorGeneralDescription(doctorId);
    }

    @GetMapping("/{doctorId}/center/{centerId}")
    public void getDoctorOfCenterDescription(@PathVariable String centerId, @PathVariable String doctorId) {

    }

    // doctor self register to system.
    @PostMapping("/register")
    public Doctor registerDoctorToSystem(@RequestBody RegisterPayload regPay) {
        Doctor doc = regPay.doctor;
        ArrayList<DoctorQualification> doctorQualifications = new ArrayList<>();
        if (regPay.qualifications.length > 0) {
            QualificationReq[] qlFs = regPay.qualifications;
            for (var qlf : qlFs) {
                doctorQualifications.add(DoctorQualification.builder().name(qlf.name).year(qlf.year).place(qlf.place).build());
            }
        }


        return doctorService.registerDoctorToSystem(doc, doctorQualifications);
    }

    @PostMapping("/register/{centerId}")
    public void registerDoctorByCenter(@RequestBody RegisterPayload regPay, @PathVariable String centerId) {


    }

    @PostMapping("/{doctorId}/qualification")
    public List<DoctorQualification> addQualifications(@RequestBody AddQualificationsPayload addQualificationsPayload, @PathVariable Long doctorId) {
        List<QualificationReq> qls = List.of(addQualificationsPayload.qualifications);
        List<DoctorQualification> doctorQualifications = new ArrayList<>();
        for (var qlf : qls) {
            doctorQualifications.add(DoctorQualification.builder().name(qlf.name).year(qlf.year).place(qlf.place).build());
        }
        return doctorQualificationService.addQualifications(doctorId, doctorQualifications);

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

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    static class AddQualificationsPayload {
        private QualificationReq[] qualifications;

    }
}
