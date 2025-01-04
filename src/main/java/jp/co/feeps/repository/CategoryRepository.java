package jp.co.feeps.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.feeps.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findByTeamTeamId(@Param("teamId") int teamId);

	Optional<Category> findByCategoryId(@Param("categoryId") int categoryId);
}
