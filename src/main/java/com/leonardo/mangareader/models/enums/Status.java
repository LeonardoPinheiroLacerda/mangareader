package com.leonardo.mangareader.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum Status {
    ATIVO ("Ativo"),
    COMPLETO ("Completo");

    private final String friendlyName;
}
