package com.exo.sec_web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api")
public class UtilisateurController {

    @GetMapping("/public/test")
    public String greet() {
        return " Public Hello and welcome";
    }

    @GetMapping("/private/test")
    public String greetPrivate() {
        return "My private get my warmest greetings";
    }

    @GetMapping("/private/user")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String routepourUser() {
        return "priavte for user ";
    }

    @GetMapping("/private/admin")
    @PreAuthorize("hasRole('ADMIn')")
    public String routePourAdminAndOther() {
        return "For admins";
    }
}
