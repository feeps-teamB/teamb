package jp.co.feeps.dto;

import java.util.Date;

import jp.co.feeps.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {
	private int scheduleId;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private Boolean isCompleted;
	private Category category;
}
