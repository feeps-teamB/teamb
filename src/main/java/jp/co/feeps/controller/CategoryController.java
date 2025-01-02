package jp.co.feeps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.dto.CategoryDTO;
import jp.co.feeps.service.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// GET http://localhost:8080/addSchedule/view/{team_id}
	@GetMapping("/addSchedule/view/{teamId}")
	public ResponseEntity<List<CategoryDTO>> getCategories(@PathVariable int teamId) {
		try {
			List<CategoryDTO> categoryDTOs = categoryService.getCategoriesByTeamId(teamId);

			// ステータス: 200 OK
			// ボディ: ユーザが参加しているカテゴリ一覧
			return ResponseEntity.status(HttpStatus.OK).body(categoryDTOs);
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}