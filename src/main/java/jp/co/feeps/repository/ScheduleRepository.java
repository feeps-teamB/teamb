package jp.co.feeps.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	// userId に紐づくデータの内、以下の条件のいずれかを満たすものを取得する
	// ・DB 上の startDate が選択した期間の範囲内にある
	// ・DB 上の endDate が選択した期間の範囲内にある
	// ・DB 上の startDate と endDate 間に選択した期間のすべてを含む
	@Query("SELECT s FROM Schedule s JOIN s.category c JOIN c.team t JOIN t.userTeams ut JOIN ut.user u WHERE u.userId = :userId AND ((s.startDate BETWEEN :startDate AND :endDate) OR (s.endDate BETWEEN :startDate AND :endDate) OR (s.startDate <= :startDate AND s.endDate >= :endDate))")
	List<Schedule> findByUserIdAndDateRange(@Param("userId") int userId, @Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	Optional<Schedule> findByScheduleId(@Param("scheduleId") int scheduleId);

	Boolean existsByScheduleId(@Param("scheduleId") int scheduleId);
}
