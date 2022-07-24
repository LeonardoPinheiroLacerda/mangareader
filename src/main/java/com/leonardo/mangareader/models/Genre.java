package com.leonardo.mangareader.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id", "name"})

@Entity
@Table(name = "GENRES")
public class Genre {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_seq")
    @SequenceGenerator(name = "genre_seq", sequenceName = "genre_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String url;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.EAGER)
    private Set<Manga> mangas = new HashSet<>();

}
