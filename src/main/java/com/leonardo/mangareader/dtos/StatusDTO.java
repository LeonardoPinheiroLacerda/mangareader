package com.leonardo.mangareader.dtos;

import com.leonardo.mangareader.models.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

public class StatusDTO {
    
    private String url;
    private Status status;

}
