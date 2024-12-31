package jp.co.feeps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
