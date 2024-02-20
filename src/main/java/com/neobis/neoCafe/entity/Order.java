package com.neobis.neoCafe.entity;

import com.neobis.neoCafe.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private String orderType;
    private OrderStatus orderStatus;
    private Date orderDate;
}
