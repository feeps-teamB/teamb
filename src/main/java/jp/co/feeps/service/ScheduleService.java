package jp.co.feeps.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.ScheduleDTO;
import jp.co.feeps.entity.Category;
import jp.co.feeps.entity.Schedule;
import jp.co.feeps.form.ScheduleForm;
import jp.co.feeps.repository.CategoryRepository;
import jp.co.feeps.repository.ScheduleRepository;

@Service
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<ScheduleDTO> getSchedulesByUserIdAndDateRange(int userId, int year, int month, Integer day) {
		// 指定された年と月から startDate と endDate を計算
		LocalDate startDate;
		LocalDate endDate;

		// day の有無により月毎と日毎の取得を分ける
		if (day != null) {
			startDate = LocalDate.of(year, month, day); // 指定の日付
			endDate = startDate; // 同日を指定
		} else {
			startDate = LocalDate.of(year, month, 1); // 月の初日
			endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 月の最終日
		}

		List<Schedule> schedules = scheduleRepository.findByUserIdAndDateRange(userId, startDate, endDate);

		// DTO にレスポンスデータを格納
		List<ScheduleDTO> scheduleDTOs = schedules.stream().map(schedule -> {
			ScheduleDTO scheduleDTO = new ScheduleDTO();
			scheduleDTO.setScheduleId(schedule.getScheduleId());
			scheduleDTO.setTitle(schedule.getTitle());
			scheduleDTO.setDescription(schedule.getDescription());
			scheduleDTO.setStartDate(schedule.getStartDate());
			scheduleDTO.setEndDate(schedule.getEndDate());
			scheduleDTO.setIsCompleted(schedule.getIsCompleted());
			scheduleDTO.setCategory(schedule.getCategory());
			scheduleDTO.setCreatedAt(schedule.getCreatedAt());
			scheduleDTO.setUpdatedAt(schedule.getUpdatedAt());

			return scheduleDTO;
		}).collect(Collectors.toList());

		return scheduleDTOs;
	}

	public Optional<ScheduleDTO> getSchedule(int scheduleId) {
		// Optional の中身を DTO に変換
		Optional<ScheduleDTO> scheduleDTOOpt = scheduleRepository.findByScheduleId(scheduleId).map(schedule -> {
			ScheduleDTO scheduleDTO = new ScheduleDTO();
			scheduleDTO.setScheduleId(schedule.getScheduleId());
			scheduleDTO.setTitle(schedule.getTitle());
			scheduleDTO.setDescription(schedule.getDescription());
			scheduleDTO.setStartDate(schedule.getStartDate());
			scheduleDTO.setEndDate(schedule.getEndDate());
			scheduleDTO.setIsCompleted(schedule.getIsCompleted());
			scheduleDTO.setCategory(schedule.getCategory());
			scheduleDTO.setCreatedAt(schedule.getCreatedAt());
			scheduleDTO.setUpdatedAt(schedule.getUpdatedAt());

			return scheduleDTO;
		});

		return scheduleDTOOpt;
	}

	public void saveSchedule(ScheduleForm scheduleForm) {
		// カテゴリのプロキシを取得
		Category category = categoryRepository.getReferenceById(scheduleForm.getCategoryId());

		// ScheduleForm を ScheduleEntity に変換
		Schedule schedule = new Schedule();
		schedule.setTitle(scheduleForm.getTitle());
		schedule.setDescription(scheduleForm.getDescription());
		schedule.setStartDate(scheduleForm.getStartDate());
		schedule.setEndDate(scheduleForm.getEndDate());
		// デフォルトの値を格納
		schedule.setIsCompleted(false);
		schedule.setCategory(category);

		scheduleRepository.save(schedule);
	}
}
