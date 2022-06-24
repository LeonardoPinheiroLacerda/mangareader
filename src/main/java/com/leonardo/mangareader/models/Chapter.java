package com.leonardo.mangareader.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"url"})

@Entity
@Table(name = "CHAPTERS")
public class Chapter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chapters_seq")
    @SequenceGenerator(name = "chapters_seq", sequenceName = "chapters_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Long visits;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    private Manga manga;
    
}
