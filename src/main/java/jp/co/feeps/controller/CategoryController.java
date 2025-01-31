package jp.co.feeps.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.dto.CategoryDTO;
import jp.co.feeps.dto.CategorySelectDTO;
import jp.co.feeps.form.CategoryEditForm;
import jp.co.feeps.form.CategoryForm;
import jp.co.feeps.service.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// GET http://localhost:8080/addSchedule/view/{teamId}
	@GetMapping("/addSchedule/view/{teamId}")
	public ResponseEntity<List<CategorySelectDTO>> getCategories(@PathVariable int teamId) {
		try {
			List<CategorySelectDTO> categoryDTOs = categoryService.getCategoriesByTeamId(teamId);

			// ステータス: 200 OK
			// ボディ: チームが保持しているカテゴリ一覧
			return ResponseEntity.status(HttpStatus.OK).body(categoryDTOs);
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// POST http://localhost:8080/addCategorySave
	@PostMapping("/addCategorySave")
	public ResponseEntity<Void> create(@RequestBody CategoryForm categoryForm) {
		try {
			categoryService.saveCategory(categoryForm);

			// ステータス: 201 Created
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// GET http://localhost:8080/categoryDetail/view/{categoryId}
	@GetMapping("/categoryDetail/view/{categoryId}")
	public ResponseEntity<CategoryDTO> show(@PathVariable int categoryId) {
		try {
			Optional<CategoryDTO> categoryDTOOpt = categoryService.getCategory(categoryId);

			// Optional を用いて空の場合の処理分けを行う
			if (categoryDTOOpt.isPresent()) {
				// ステータス: 200 OK
				// ボディ: 選択したカテゴリ
				return ResponseEntity.ok(categoryDTOOpt.get());
			} else {
				// ステータス: 404 Not Found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
  
  // PUT http://localhost:8080/categoryEdit
	@PutMapping("/categoryEdit")
	public ResponseEntity<Void> update(@RequestBody CategoryEditForm categoryEditForm) {
		try {
			categoryService.updateCategory(categoryEditForm);

			// ステータス: 200 OK
			return ResponseEntity.ok().build();
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// DELETE http://localhost:8080/deleteCategory/{categoryId}
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<Void> delete(@PathVariable int categoryId) {
		try {
			boolean isDeleted = categoryService.deleteCategory(categoryId);

			// 削除処理による処理分けを行う
			if (isDeleted) {
				// ステータス: 204 No Content
				return ResponseEntity.noContent().build();
			} else {
				// ステータス: 404 Not Found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}