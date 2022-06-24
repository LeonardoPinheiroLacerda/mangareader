package com.leonardo.mangareader.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.dtos.MangaHistoryDTO;
import com.leonardo.mangareader.services.MangaHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/history")
public class HistoryController {
    
    private final MangaHistoryService service;

    @GetMapping
    public ModelAndView index(){

        List<MangaHistoryDTO> history = service.getUserHistory();

        ModelAndView modelAndView = new ModelAndView("screens/history");
        modelAndView.addObject("history", history);
        return modelAndView;
    }

}
