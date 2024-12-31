package jp.co.feeps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.form.ScheduleForm;
import jp.co.feeps.service.ScheduleService;

@RestController
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;

	// POST http://localhost:8080/addScheduleSave
	@PostMapping("/addScheduleSave")
	public ResponseEntity<Void> create(@RequestBody ScheduleForm scheduleForm) {
		scheduleService.saveSchedule(scheduleForm);

		// ステータス: 201 Created
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
