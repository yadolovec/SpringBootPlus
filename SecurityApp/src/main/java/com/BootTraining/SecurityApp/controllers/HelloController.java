package com.BootTraining.SecurityApp.controllers;

import com.BootTraining.SecurityApp.security.PersonDetails;
import com.BootTraining.SecurityApp.services.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final AdminService adminService;

    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/showInfo")
    public String showInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPager(){
        adminService.doAdminStuff();
        return "admin";
    }

}
