package com.example.my_store__pet_project.model;

import com.example.my_store__pet_project.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Artem Kovalov on 24.02.2023
 */
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column
    private BigDecimal amount;

    @Column
    private LocalDate dateAdded;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private Users user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList;
}
