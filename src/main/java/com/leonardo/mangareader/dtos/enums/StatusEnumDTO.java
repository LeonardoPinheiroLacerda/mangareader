package com.leonardo.mangareader.dtos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum StatusEnumDTO {
    ATIVO ("Ativo"),
    COMPLETO ("Completo");

    private final String friendlyName;
}
