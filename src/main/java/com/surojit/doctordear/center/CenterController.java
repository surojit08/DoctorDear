package com.surojit.doctordear.center;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/center")
public class CenterController {

    @Autowired
    CenterService centerService;


    @PostMapping("/{hospitalId}")
    public Center registerCenter(@RequestBody Center center, @PathVariable Long hospitalId) {
        return centerService.registerCenter(center, hospitalId);
    }
}
