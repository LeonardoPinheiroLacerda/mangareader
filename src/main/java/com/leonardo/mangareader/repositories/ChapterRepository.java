package com.leonardo.mangareader.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long>{

    @Query("SELECT chapter FROM Chapter chapter WHERE chapter.url = ?1")
    public Optional<Chapter> findByUrl(String url);
    
}
