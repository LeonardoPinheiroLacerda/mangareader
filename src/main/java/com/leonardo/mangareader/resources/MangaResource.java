package com.leonardo.mangareader.resources;

import java.util.List;

import com.leonardo.mangareader.dtos.MangaDTO;
import com.leonardo.mangareader.dtos.SimpleMangaDTO;
import com.leonardo.mangareader.services.MangaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("/api/mangas")
public class MangaResource {
    
    private final MangaService service;

    @GetMapping
    public ResponseEntity<List<SimpleMangaDTO>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<MangaDTO> createFromUrl(@RequestParam(required = true) String url){
        return ResponseEntity.ok(service.createFromUrl(url));
    }

}
