package com.leonardo.mangareader.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "CHAPTER_HISTORIES")
public class ChapterHistory {
    
    @EmbeddedId
    private ChapterHistoryPK id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;
    
    @Column(nullable = true)
    private LocalDateTime lastReadAt;

}
