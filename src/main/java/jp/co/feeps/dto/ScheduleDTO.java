package jp.co.feeps.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jp.co.feeps.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTO {
	private int scheduleId;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean isCompleted;
	private Category category;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
