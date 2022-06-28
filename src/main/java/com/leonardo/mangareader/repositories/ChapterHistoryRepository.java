package com.leonardo.mangareader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.ChapterHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;

@Repository
public interface ChapterHistoryRepository extends JpaRepository<ChapterHistory, ChapterHistoryPK> {

    @Query("SELECT h FROM ChapterHistory h WHERE h.id.user = ?1 AND h.readStatus != 'NONE' ORDER BY h.lastReadAt desc")
    public Optional<List<ChapterHistory>> findUserHistory(User user);

}
