package com.leonardo.mangareader.controllers;

import com.leonardo.mangareader.services.MangaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/manga")
public class MangaController {

    private final MangaService mangaService;

    @PostMapping
    public ModelAndView index(@ModelAttribute(name = "url") String url, ModelMap model){
        model.put("manga", mangaService.createFromUrl(url));
        return new ModelAndView("redirect:/manga", model);
    }

    @GetMapping
    public ModelAndView index(ModelMap model){
        ModelAndView modelAndView = new ModelAndView("screens/manga", model);
        return modelAndView;
    }
}
