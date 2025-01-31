package jp.co.feeps.form;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleForm {
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int categoryId;
}
