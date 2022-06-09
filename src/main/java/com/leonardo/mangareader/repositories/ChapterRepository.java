package com.leonardo.mangareader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer>{
    
}
