package com.neobis.neoCafe.repository;

import com.neobis.neoCafe.entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkScheduleRepo extends JpaRepository<WorkSchedule, Long> {
}
