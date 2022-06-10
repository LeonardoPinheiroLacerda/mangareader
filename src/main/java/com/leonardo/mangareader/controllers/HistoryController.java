package com.leonardo.mangareader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/history")
public class HistoryController {
    
    @GetMapping
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("screens/history");

        return modelAndView;
    }

}
