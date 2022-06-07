package com.leonardo.mangareader.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.leonardo.mangareader.models.enums.ReadStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "CHAPTERS")
public class Chapter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chapter_seq")
    @SequenceGenerator(name = "chapter_seq", sequenceName = "chapter_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    @ManyToOne
    private Manga manga;

    @Column(nullable = true)
    private Chapter previous;

    @Column(nullable = true)
    private Chapter next;

    @Column(nullable = false)
    private ReadStatus readStatus = ReadStatus.NONE;
    
}
