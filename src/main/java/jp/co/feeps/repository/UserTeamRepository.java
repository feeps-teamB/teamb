package jp.co.feeps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.UserTeam;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Integer> {

}
