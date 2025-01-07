package jp.co.feeps.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
	@Query("SELECT t FROM Team t JOIN t.userTeams ut JOIN ut.user u WHERE u.userId = :userId")
	List<Team> findTeamsByUserId(@Param("userId") int userId);

	Optional<Team> findByTeamId(@Param("teamId") int teamid);

	boolean existsByName(@Param("name") String name);

	// 名前が変更される場合のみ存在チェックを実施
	@Query("SELECT COUNT(t) > 0 FROM Team t WHERE t.name = :name AND t.teamId != :teamId")
	boolean existsByNameAndNotTeamId(@Param("name") String name, @Param("teamId") int teamId);
}