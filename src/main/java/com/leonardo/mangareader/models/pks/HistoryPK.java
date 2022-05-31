package com.leonardo.mangareader.models.pks;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.leonardo.mangareader.models.Manga;
import com.leonardo.mangareader.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Embeddable
public class HistoryPK implements Serializable{
    private static final long serialVersionUID = 1L;

    @OneToOne
    private User user;

    @ManyToOne
    private Manga manga;

}
