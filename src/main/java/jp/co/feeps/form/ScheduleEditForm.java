package jp.co.feeps.form;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleEditForm {
	private int scheduleId;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean isCompleted;
	private int categoryId;
}