package jp.co.feeps.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryEditForm {
	private int categoryId;
	private String name;
	private String color;
	private int teamId;
}
