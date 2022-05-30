package com.leonardo.mangareader.resources;

import com.leonardo.mangareader.dtos.ChapterPagesDTO;
import com.leonardo.mangareader.services.ChapterService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("/api/chapters")
public class ChapterResource {
    
    private final ChapterService service;

    @GetMapping
    public ResponseEntity<ChapterPagesDTO> getFromUrl(@RequestParam String url){
        return ResponseEntity.ok(service.getFromUrl(url));
    }

}
