package jp.co.feeps.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "teams")
@Getter
@Setter
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int teamId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@OneToMany(mappedBy = "team")
	@JsonIgnore
	private List<UserTeam> userTeams;
	@OneToMany(mappedBy = "team")
	@JsonIgnore
	private List<Category> categories;
}
