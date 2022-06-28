package com.leonardo.mangareader.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.leonardo.mangareader.dtos.ChapterHistoryDTO;
import com.leonardo.mangareader.exceptions.ObjectNotFoundException;
import com.leonardo.mangareader.models.Chapter;
import com.leonardo.mangareader.models.ChapterHistory;
import com.leonardo.mangareader.models.User;
import com.leonardo.mangareader.models.enums.ReadStatus;
import com.leonardo.mangareader.models.pks.ChapterHistoryPK;
import com.leonardo.mangareader.repositories.ChapterHistoryRepository;
import com.leonardo.mangareader.repositories.ChapterRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor

@Service 
public class ChapterHistoryService {
    
    private final ChapterRepository chapterRepository;
    private final ChapterHistoryRepository repository;
    private final UserService userService;
    private final DtoMapperService dtoMapperService;
    
    @Transactional
    public ChapterHistoryDTO findById(ChapterHistoryPK id){
        ChapterHistory obj = repository.findById(id).orElseGet(null);
        
        if(obj == null){
            return null;
        }

        return dtoMapperService.chapterHistoryTochapterHistoryDTO(obj);
    }

    @Transactional
    public ReadStatus changeReadStatus(Long id, ReadStatus status){
        
        Chapter chapter = chapterRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("O capitulo de id " + id + " não pode ser encontrado."));

        User user = userService.getLogged();

        ChapterHistoryPK pk = new ChapterHistoryPK(user, chapter);

        ChapterHistory history = repository.findById(pk).orElse(null);

        if(history == null){
            doHistory(pk);
            history = repository.findById(pk).get();
        }  

        if(!history.getReadStatus().equals(ReadStatus.READ)){
            history.setReadStatus(status);
        }

        history.setLastReadAt(LocalDateTime.now());

        repository.save(history);

        return status;
    }

    @Transactional
    public List<ChapterHistoryDTO> getUserHistory(){
        List<ChapterHistory> history =  repository
            .findUserHistory(userService.getLogged())
            .get();
        
        return history
            .stream()
            .map(h -> dtoMapperService.chapterHistoryTochapterHistoryDTO(h))
            .sorted((h1, h2) -> h2.getLastRead().compareTo(h1.getLastRead()))
            .collect(Collectors.toList());
    }

    @Transactional
    public ChapterHistory doHistory(ChapterHistoryPK pk) {
        ChapterHistory history = new ChapterHistory(pk, ReadStatus.NONE, LocalDateTime.now());
        return repository.save(history);
    }

    @Transactional
    public void checkAndDoHistory(Chapter chapter, User user){

        ChapterHistoryPK pk = new ChapterHistoryPK(user, chapter);
    
        if(repository.findById(pk).isEmpty()){
            doHistory(pk);
        }

    }

    @Transactional
    public void delete(ChapterHistoryPK pk) {
        ChapterHistory history = repository.findById(pk).orElse(null);
        if(history == null) return;
        repository.delete(history);
    }
}
