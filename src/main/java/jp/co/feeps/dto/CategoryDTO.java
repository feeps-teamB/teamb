package jp.co.feeps.dto;

import java.time.LocalDateTime;

import jp.co.feeps.entity.Team;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
	private int categoryId;
	private String name;
	private String color;
	private Team team;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}