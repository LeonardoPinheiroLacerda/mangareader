package com.leonardo.mangareader.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.leonardo.mangareader.models.enums.Status;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cover;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = true)
    @Lob
    private String synopsis;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = true)
    private Float score;

    @Column(nullable = true)
    private Integer scoredBy;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Artist artist;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "MANGA_GENRE",
        joinColumns = @JoinColumn(name = "manga_id"), 
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "manga", fetch = FetchType.EAGER)
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "id.manga", fetch = FetchType.EAGER)
    private Set<History> histories = new HashSet<>();

}

