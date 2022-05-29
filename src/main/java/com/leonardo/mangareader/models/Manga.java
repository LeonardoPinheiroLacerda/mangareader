package com.leonardo.mangareader.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "MANGAS")
public class Manga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manga_seq")
    @SequenceGenerator(name = "manga_seq", sequenceName = "manga_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    private String name;
    private String cover;
    private String url;
    private LocalDateTime lastRead;

}
