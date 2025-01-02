package jp.co.feeps.form;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleForm {
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int categoryId;
}
