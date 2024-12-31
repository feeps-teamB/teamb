package jp.co.feeps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.TeamDTO;
import jp.co.feeps.entity.Team;
import jp.co.feeps.repository.TeamRepository;

@Service
public class TeamService {
	@Autowired
	private TeamRepository teamRepository;

	public List<TeamDTO> getTeamsByUser(int userId) {
		List<Team> teams = teamRepository.findTeamsByUserId(userId);

		// DTO にレスポンスデータを格納
		List<TeamDTO> teamDTOs = teams.stream().map(team -> {
			TeamDTO teamDTO = new TeamDTO();
			teamDTO.setTeamId(team.getTeamId());
			teamDTO.setName(team.getName());

			return teamDTO;
		}).collect(Collectors.toList());

		return teamDTOs;
	}
}