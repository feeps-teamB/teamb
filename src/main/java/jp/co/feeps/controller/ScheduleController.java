package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.form.ScheduleForm;
import jp.co.feeps.service.ScheduleService;

@RestController
@CrossOrigin
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

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
}
