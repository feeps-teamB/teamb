package jp.co.feeps.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.dto.ScheduleDTO;
import jp.co.feeps.form.ScheduleForm;
import jp.co.feeps.service.ScheduleService;

@RestController
@CrossOrigin
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

	// GET http://localhost:8080/mainPage/view/{userId}/{year}/{month}
	@GetMapping("/mainPage/view/{userId}/{year}/{month}")
	public ResponseEntity<List<ScheduleDTO>> index(@PathVariable int userId, @PathVariable int year,
			@PathVariable int month) {
		try {
			List<ScheduleDTO> scheduleDTOs = scheduleService.getSchedulesByUserIdAndDateRange(userId, year, month);

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

	// GET http://localhost:8080/scheduleDetail/view/{schedule_id}
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
}
