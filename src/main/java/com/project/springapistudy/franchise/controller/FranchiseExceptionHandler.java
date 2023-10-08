package com.project.springapistudy.franchise.controller;

import com.project.springapistudy.franchise.domain.DuplicateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackageClasses = FranchiseController.class)
public class FranchiseExceptionHandler {
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<String> duplicateExceptionHandler(DuplicateException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
