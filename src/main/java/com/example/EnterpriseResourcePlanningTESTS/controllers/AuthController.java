package com.example.EnterpriseResourcePlanningTESTS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AuthController {

    @PostMapping("/fail_login")
    public String handleFailedLogin() {
        System.out.println("A user has failed to login");

        return "redirect:/login?error";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "Auth/login";
    }


}
