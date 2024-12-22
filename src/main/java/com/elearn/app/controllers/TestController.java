package com.elearn.app.controllers;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String testing(){
        return "Testing";
    }


    @GetMapping("/test")
    @PreAuthorize("hasRole('GUEST')")
    public String testing1(){
        return "testing1";
    }

    @GetMapping("/all")
    public String all(){
        return "opi api end point";
    }


}
