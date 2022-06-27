package com.leonardo.mangareader.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.services.ChapterHistoryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@RestController
@RequestMapping("/api/chapterhistory")
public class ChapterHistoryResource {

    private final ChapterHistoryService service;

    @PutMapping("/setviewed/{id}")
    public ResponseEntity<ReadStatus> setViewed(@PathVariable Long id){
        return ResponseEntity.ok(service.changeReadStatus(id, ReadStatus.VIEWED));
    }

    @PutMapping("/setread/{id}")
    public ResponseEntity<ReadStatus> setRead(@PathVariable Long id){
        return ResponseEntity.ok(service.changeReadStatus(id, ReadStatus.READ));
    }
    
}
