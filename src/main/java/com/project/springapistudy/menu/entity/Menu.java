package com.project.springapistudy.menu.entity;

import com.project.springapistudy.franchise.entity.Franchise;
import com.project.springapistudy.menu.domain.Amount;
import com.project.springapistudy.product.entity.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MENU_ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "FRANCHISE_ID")
    private Franchise franchise;

    @Embedded
    private Amount amount;

    @Column(name = "SALE_START_DATE")
    private LocalDateTime saleStartDate;

    @Column(name = "SALE_END_DATE")
    private LocalDateTime saleEndDate;

    
}
