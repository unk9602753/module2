package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseException {
    private String message;
    private int code;
}
