package com.leonardo.mangareader.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cover;

    @Column(nullable = false, unique = true)
    private String url;

    @OneToMany(mappedBy = "id.manga")
    private Set<History> histories = new HashSet<>();

    public Manga(Integer id, String title, String cover, String url) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.url = url;
    }

}

