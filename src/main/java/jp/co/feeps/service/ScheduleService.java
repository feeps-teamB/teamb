package jp.co.feeps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public void saveSchedule(ScheduleForm scheduleForm) {
		// カテゴリのプロキシを取得
		Category category = categoryRepository.getReferenceById(scheduleForm.getCategoryId());

		// ScheduleFormをScheduleEntityに変換
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
