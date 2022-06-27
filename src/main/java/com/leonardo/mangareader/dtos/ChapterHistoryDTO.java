package com.leonardo.mangareader.dtos;

import java.time.LocalDateTime;

import com.leonardo.mangareader.models.enums.ReadStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ChapterHistoryDTO {
    
    private ChapterDTO chapter;
    private LocalDateTime lastRead;
    private ReadStatus status;

}
