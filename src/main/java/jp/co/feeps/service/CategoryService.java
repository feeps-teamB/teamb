package jp.co.feeps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.CategoryDTO;
import jp.co.feeps.entity.Category;
import jp.co.feeps.repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

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
}
