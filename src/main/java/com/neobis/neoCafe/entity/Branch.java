package com.neobis.neoCafe.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;
    private String address;
    private String gisUrl;
    private String phoneNumber;
    private int tableCount;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;
    @OneToMany(mappedBy = "branch")
    @ToString.Exclude
    private List<Product> availableProducts;

}
