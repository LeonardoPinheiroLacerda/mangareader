package com.leonardo.mangareader.controllers;

import org.checkerframework.checker.units.qual.m;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.services.ChapterGetterFactoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/reader")
public class ReaderController {
 
    private final ChapterGetterFactoryService chapterGetterFactoryService;

    @GetMapping
    public ModelAndView index(@RequestParam String url){
        ModelAndView modelAndView = new ModelAndView("screens/reader");
        modelAndView.addObject("chapter", chapterGetterFactoryService.getInstance(url).getFromUrl());
        return modelAndView;
    }

}
