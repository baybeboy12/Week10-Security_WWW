package com.example.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class adminController {
    @GetMapping()
    public String admin(){
        return "This is admin area";
    }
}
