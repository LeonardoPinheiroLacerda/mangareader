package com.leonardo.mangareader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.MangaHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.MangaHistoryPK;

@Repository
public interface MangaHistoryRepository extends JpaRepository<MangaHistory, MangaHistoryPK> {

    @Query("SELECT h FROM MangaHistory h WHERE h.id.user = ?1")
    public Optional<List<MangaHistory>> findUserHistory(User user);

}
