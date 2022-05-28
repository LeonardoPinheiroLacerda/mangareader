package com.leonardo.mangareader.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class StandardError implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Long timestamp;
	private Integer status;
	private Object error;
	private String message;
	private String path;

}
