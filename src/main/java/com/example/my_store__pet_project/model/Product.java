package com.example.my_store__pet_project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column
    private String model;

    @Column
    private BigDecimal price;

    @Column
    private LocalDate dateAdded;

    @Column
    private String image;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_stock_status_id", referencedColumnName = "id")
    private StockStatus stockStatus;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "fk_category_id", referencedColumnName = "id")
    private Category category;

    @OneToOne(mappedBy = "product")
    @PrimaryKeyJoinColumn
    private ProductDetails productDetails;
}
