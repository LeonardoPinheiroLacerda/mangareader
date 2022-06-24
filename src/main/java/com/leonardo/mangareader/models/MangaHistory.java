package com.leonardo.mangareader.models;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.leonardo.mangareader.models.pks.MangaHistoryPK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "MANGA_HISTORIES")
public class MangaHistory {

    @EmbeddedId
    private MangaHistoryPK id;

    private LocalDateTime lastReadAt;

    @ManyToOne
    private Chapter lastChapterRead;
    
}
