package com.leonardo.mangareader.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.mangareader.services.HistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("/api/history")
public class HistoryResource {

    private final HistoryService service;

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam String url){
        service.delete(url);
        return ResponseEntity.noContent().build();
    }

}
