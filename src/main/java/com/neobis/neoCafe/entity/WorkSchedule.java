package com.neobis.neoCafe.entity;

import com.neobis.neoCafe.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "work_schedules")
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_work_schedule",
            joinColumns = @JoinColumn(name = "work_schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @ToString.Exclude
    private List<User> users;
    @OneToOne(mappedBy = "workSchedule")
    private Branch branch;
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> dayOfWeek;
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;
    @Temporal(TemporalType.TIME)
    private LocalTime endTime;
}
