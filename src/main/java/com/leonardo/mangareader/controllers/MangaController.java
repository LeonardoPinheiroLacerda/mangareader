package com.leonardo.mangareader.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.exceptions.NotSuportedSourceException;
import com.leonardo.mangareader.exceptions.SourceException;
import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.pks.MangaHistoryPK;
import com.leonardo.mangareader.services.MangaHistoryService;
import com.leonardo.mangareader.services.MangaService;
import com.leonardo.mangareader.services.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Controller
@RequestMapping("/manga")
public class MangaController {

    private final UserService userService;
    private final MangaService mangaService;
    private final MangaHistoryService historyService;

    @GetMapping
    public ModelAndView index(@RequestParam String url) {
        try {
            MangaDTO manga = mangaService.createFromUrl(url);

            ModelAndView modelAndView = new ModelAndView("screens/manga");
            modelAndView.addObject("manga", manga);
            modelAndView.addObject("history", historyService.getMangaHistory(url));

            Optional<Manga> mangaOpt = mangaService.findByUrl(url);

            if(mangaOpt.isPresent()){
                historyService.doHistory(new MangaHistoryPK(userService.getLogged(), mangaOpt.get()));
            }
            
            
            return modelAndView;
        } catch (NotSuportedSourceException e) {
            return new ModelAndView("redirect:/?err=notSuportedSource");
        } catch(SourceException e){
            return new ModelAndView("redirect:/?err=source");
        }
    }
}
