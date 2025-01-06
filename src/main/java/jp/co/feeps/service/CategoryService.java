package jp.co.feeps.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.CategoryDTO;
import jp.co.feeps.dto.CategorySelectDTO;
import jp.co.feeps.entity.Category;
import jp.co.feeps.entity.Team;
import jp.co.feeps.form.CategoryEditForm;
import jp.co.feeps.form.CategoryForm;
import jp.co.feeps.repository.CategoryRepository;
import jp.co.feeps.repository.TeamRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	TeamRepository teamRepository;

	public List<CategorySelectDTO> getCategoriesByTeamId(int teamId) {
		List<Category> categories = categoryRepository.findByTeamTeamId(teamId);

		// DTO にレスポンスデータを格納
		List<CategorySelectDTO> categorySelectDTOs = categories.stream().map(category -> {
			CategorySelectDTO categorySelectDTO = new CategorySelectDTO();
			categorySelectDTO.setCategoryId(category.getCategoryId());
			categorySelectDTO.setName(category.getName());
			categorySelectDTO.setColor(category.getColor());

			return categorySelectDTO;
		}).collect(Collectors.toList());

		return categorySelectDTOs;
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

	public Optional<CategoryDTO> getCategory(int categoryId) {
		// Optional の中身を DTO に変換
		Optional<CategoryDTO> categoryDTOOpt = categoryRepository.findByCategoryId(categoryId).map(category -> {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setCategoryId(category.getCategoryId());
			categoryDTO.setName(category.getName());
			categoryDTO.setColor(category.getColor());
			categoryDTO.setTeam(category.getTeam());
			categoryDTO.setCreatedAt(category.getCreatedAt());
			categoryDTO.setUpdatedAt(category.getUpdatedAt());

			return categoryDTO;
		});

		return categoryDTOOpt;
	}

	public void updateCategory(CategoryEditForm categoryEditForm) {
		// チームのプロキシを取得
		Team team = teamRepository.getReferenceById(categoryEditForm.getTeamId());

		// CategoryEditForm を CategoryEntity に変換
		Category category = new Category();
		category.setCategoryId(categoryEditForm.getCategoryId());
		category.setName(categoryEditForm.getName());
		category.setColor(categoryEditForm.getColor());
		category.setTeam(team);

		categoryRepository.save(category);

	}
  
  public Boolean deleteCategory(int categoryId) {
		// categoryId の存在確認
		if (categoryRepository.existsByCategoryId(categoryId)) {
			categoryRepository.deleteById(categoryId);

			return true;
		} else {
			return false;
		}
  }
}
