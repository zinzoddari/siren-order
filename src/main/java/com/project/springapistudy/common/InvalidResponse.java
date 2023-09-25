package com.project.springapistudy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidResponse {
    private String field;

    private String message;

    private Object rejectValue;
}
