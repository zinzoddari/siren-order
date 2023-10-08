package com.project.springapistudy.franchise.domain;

import com.sun.jdi.request.DuplicateRequestException;

public class DuplicateException extends DuplicateRequestException {
    public DuplicateException() {
        super("중복된 데이터입니다.");
    }

    public DuplicateException(String s) {
        super(s);
    }
}
