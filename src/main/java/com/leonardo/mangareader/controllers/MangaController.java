package com.leonardo.mangareader.controllers;

import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.exceptions.SourceException;
import com.leonardo.mangareader.services.MangaService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/manga")
public class MangaController {

    private final MangaService mangaService;

    @GetMapping
    public ModelAndView index(@RequestParam String url) {
        try {
            ModelAndView modelAndView = new ModelAndView("screens/manga");
            modelAndView.addObject("manga", mangaService.createFromUrl(url));
            
            return modelAndView;
        } catch (NotSuportedSourceException e) {
            return new ModelAndView("redirect:/?err=notSuportedSource");
        } catch(SourceException e){
            return new ModelAndView("redirect:/?err=source");
        }
    }
}
