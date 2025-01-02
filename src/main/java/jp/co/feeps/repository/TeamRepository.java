package jp.co.feeps.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
	@Query("SELECT t FROM Team t JOIN t.userTeams ut WHERE ut.user.userId = :userId")
	List<Team> findTeamsByUserId(int userId);
}