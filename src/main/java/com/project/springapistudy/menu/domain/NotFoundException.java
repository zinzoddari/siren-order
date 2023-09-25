package com.project.springapistudy.menu.domain;

import javax.persistence.NoResultException;

public class NotFoundException extends NoResultException {
    public NotFoundException() {
        super("조회된 데이터가 없습니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
