package com.leonardo.mangareader.models;

import javax.persistence.Column;
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
@EqualsAndHashCode()

@Entity
@Table(name = "ARTISTS")
public class Artist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_seq")
    @SequenceGenerator(name = "artist_seq", sequenceName = "artist_seq", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String url;


}
