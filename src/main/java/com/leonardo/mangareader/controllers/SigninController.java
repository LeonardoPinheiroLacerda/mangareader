package com.leonardo.mangareader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/signin")
public class SigninController {
    
    @GetMapping
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("signin");
        return modelAndView;
    }

}
