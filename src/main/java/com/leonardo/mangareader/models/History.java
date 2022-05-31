package com.leonardo.mangareader.models;

import java.time.LocalDateTime;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.leonardo.mangareader.models.pks.HistoryPK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "HISTORIES")
public class History {

    @EmbeddedId
    private HistoryPK id;

    private LocalDateTime lastRead;

    @Lob
    private String chaptersRead;
    
}
