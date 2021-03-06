package com.leonardo.mangareader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.MangaHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.MangaHistoryPK;

@Repository
public interface MangaHistoryRepository extends PagingAndSortingRepository<MangaHistory, MangaHistoryPK> {

    @Query("SELECT h FROM MangaHistory h WHERE h.id.user = ?1 ORDER BY h.lastReadAt desc")
    public Optional<List<MangaHistory>> findUserHistory(User user);

    @Query("SELECT h FROM MangaHistory h WHERE h.id.user = ?1 ORDER BY h.lastReadAt desc")
    public Page<MangaHistory> findUserHistory(User user, Pageable pageable);

}
