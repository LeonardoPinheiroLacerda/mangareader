package com.leonardo.mangareader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.ChapterHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;

@Repository
public interface ChapterHistoryRepository extends PagingAndSortingRepository<ChapterHistory, ChapterHistoryPK> {

    @Query("SELECT h FROM ChapterHistory h WHERE h.id.user = ?1 AND h.readStatus != 'NONE' ORDER BY h.lastReadAt desc")
    public Optional<List<ChapterHistory>> findUserHistory(User user);

    @Query("SELECT h FROM ChapterHistory h WHERE h.id.user = ?1 AND h.readStatus != 'NONE' ORDER BY h.lastReadAt desc")
    public Page<ChapterHistory> findNonViewed(User user, Pageable pageable);

}
