package jp.co.feeps.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "userTeams")
@Getter
@Setter
public class UserTeam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userTeamId;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "team_id", referencedColumnName = "teamId", nullable = false)
	private Team team;
}
