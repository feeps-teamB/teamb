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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.dto.ScheduleDTO;
import jp.co.feeps.form.ScheduleEditForm;
import jp.co.feeps.form.ScheduleForm;
import jp.co.feeps.service.ScheduleService;

@RestController
@CrossOrigin
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

	// GET http://localhost:8080/calendar/view/{userId}/{year}/{month}
	@GetMapping("/calendar/view/{userId}/{year}/{month}")
	public ResponseEntity<List<ScheduleDTO>> index(@PathVariable int userId, @PathVariable int year,
			@PathVariable int month, @RequestParam(required = false) Integer day) {
		try {
			List<ScheduleDTO> scheduleDTOs = scheduleService.getSchedulesByUserIdAndDateRange(userId, year, month, day);

			// ステータス: 200 OK
			// ボディ: 選択したユーザが持ち、選択した期間に登録した期間が被るスケジュール一覧
			return ResponseEntity.ok(scheduleDTOs);
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// POST http://localhost:8080/addScheduleSave
	@PostMapping("/addScheduleSave")
	public ResponseEntity<Void> create(@RequestBody ScheduleForm scheduleForm) {
		try {
			scheduleService.saveSchedule(scheduleForm);

			// ステータス: 201 Created
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// GET http://localhost:8080/scheduleDetail/view/{scheduleId}
	@GetMapping("/scheduleDetail/view/{scheduleId}")
	public ResponseEntity<ScheduleDTO> show(@PathVariable int scheduleId) {
		try {
			Optional<ScheduleDTO> scheduleDTOOpt = scheduleService.getSchedule(scheduleId);

			// Optional を用いて空の場合の処理分けを行う
			if (scheduleDTOOpt.isPresent()) {
				// ステータス: 200 OK
				// ボディ: 選択したスケジュール
				return ResponseEntity.ok(scheduleDTOOpt.get());
			} else {
				// ステータス: 404 Not Found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
  
  // PUT http://localhost:8080/scheduleEdit
	@PutMapping("/scheduleEdit")
	public ResponseEntity<Void> update(@RequestBody ScheduleEditForm scheduleEditForm) {
		try {
			scheduleService.updateSchedule(scheduleEditForm);

			// ステータス: 200 OK
			return ResponseEntity.ok().build();
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// DELETE http://localhost:8080/deleteSchedule/{scheduleId}
	@DeleteMapping("/deleteSchedule/{scheduleId}")
	public ResponseEntity<Void> delete(@PathVariable int scheduleId) {
		try {
			boolean isDeleted = scheduleService.deleteSchedule(scheduleId);

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