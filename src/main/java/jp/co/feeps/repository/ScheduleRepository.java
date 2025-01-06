package jp.co.feeps.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	Optional<Schedule> findByScheduleId(@Param("scheduleId") int scheduleId);

	Boolean existsByScheduleId(@Param("scheduleId") int scheduleId);
}
