package com.project.springapistudy.menu.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {
    @Column(name = "AMOUNT")
    private BigDecimal value;
}
