package com.leonardo.mangareader.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.leonardo.mangareader.models.History;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.pks.HistoryPK;

@Repository
public interface HistoryRepository extends JpaRepository<History, HistoryPK> {

    @Query("SELECT h FROM History h WHERE h.id.user = ?1")
    public Optional<List<History>> findUserHistory(User user);

}
