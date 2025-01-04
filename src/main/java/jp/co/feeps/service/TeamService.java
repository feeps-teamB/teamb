package jp.co.feeps.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.TeamSelectDTO;
import jp.co.feeps.entity.Team;
import jp.co.feeps.repository.TeamRepository;

@Service
public class TeamService {
	@Autowired
	private TeamRepository teamRepository;

	public List<TeamSelectDTO> getTeamsByUserId(int userId) {
		List<Team> teams = teamRepository.findTeamsByUserId(userId);

		// DTO にレスポンスデータを格納
		List<TeamSelectDTO> TeamSelectDTOs = teams.stream().map(team -> {
			TeamSelectDTO TeamSelectDTO = new TeamSelectDTO();
			TeamSelectDTO.setTeamId(team.getTeamId());
			TeamSelectDTO.setName(team.getName());

			return TeamSelectDTO;
		}).collect(Collectors.toList());

		return TeamSelectDTOs;
	}
}