package com.leonardo.mangareader.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.leonardo.mangareader.models.enums.ReadStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"url"})

@ToString(exclude = {"manga", "previous", "next"})

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

    @Column(nullable = true)
    private String number;

    @ManyToOne
    private Manga manga;

    @OneToOne
    private Chapter previous;

    @OneToOne
    private Chapter next;

    @Column(nullable = false)
    private ReadStatus readStatus = ReadStatus.NONE;
    
}
