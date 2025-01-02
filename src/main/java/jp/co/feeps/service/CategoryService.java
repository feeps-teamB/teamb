package jp.co.feeps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.CategoryDTO;
import jp.co.feeps.entity.Category;
import jp.co.feeps.entity.Team;
import jp.co.feeps.form.CategoryForm;
import jp.co.feeps.repository.CategoryRepository;
import jp.co.feeps.repository.TeamRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	TeamRepository teamRepository;

	public List<CategoryDTO> getCategoriesByTeamId(int teamId) {
		List<Category> categories = categoryRepository.findByTeamTeamId(teamId);

		// DTO にレスポンスデータを格納
		List<CategoryDTO> categoryDTOs = categories.stream().map(category -> {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCategoryId(category.getCategoryId());
			categoryDTO.setName(category.getName());
			categoryDTO.setColor(category.getColor());

			return categoryDTO;
		}).collect(Collectors.toList());

		return categoryDTOs;
	}

	public void saveCategory(CategoryForm categoryForm) {
		// チームのプロキシを取得
		Team team = teamRepository.getReferenceById(categoryForm.getTeamId());

		// CategoryForm を CategoryEntity に変換
		Category category = new Category();
		category.setName(categoryForm.getName());
		category.setColor(categoryForm.getColor());
		category.setTeam(team);

		categoryRepository.save(category);
	}
}