package com.neobis.neoCafe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
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
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WorkSchedule> workSchedule;
    @OneToMany(mappedBy = "branch")
    @ToString.Exclude
    private List<Product> availableProducts;

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", address='" + address + '\'' +
                ", gisUrl='" + gisUrl + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tableCount=" + tableCount +
                ", workSchedule=" + workSchedule +
                ", availableProducts=" + availableProducts +
                '}';
    }
}
