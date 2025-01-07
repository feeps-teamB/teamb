package jp.co.feeps.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.feeps.dto.TeamDTO;
import jp.co.feeps.dto.TeamSelectDTO;
import jp.co.feeps.entity.Team;
import jp.co.feeps.entity.User;
import jp.co.feeps.entity.UserTeam;
import jp.co.feeps.exception.ErrorMessages;
import jp.co.feeps.form.TeamEditForm;
import jp.co.feeps.form.TeamForm;
import jp.co.feeps.repository.TeamRepository;
import jp.co.feeps.repository.UserRepository;
import jp.co.feeps.repository.UserTeamRepository;

@Service
public class TeamService {
	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserTeamRepository userTeamRepository;

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

	public Optional<TeamDTO> getTeam(int teamId) {
		// Optional の中身を DTO に変換
		Optional<TeamDTO> teamDTOOpt = teamRepository.findByTeamId(teamId).map(team -> {
			TeamDTO teamDTO = new TeamDTO();
			teamDTO.setName(team.getName());
			teamDTO.setDescription(team.getDescription());

			return teamDTO;
		});

		return teamDTOOpt;
	}

	public void saveTeam(int userId, TeamForm teamForm) {
		// チーム名がすでに存在するか確認
		if (teamRepository.existsByName(teamForm.getName())) {
			throw new IllegalArgumentException(ErrorMessages.TEAM_NAME_ALREADY_EXISTS);
		}

		// TeamForm を TeamEntity に変換
		Team team = new Team();
		team.setName(teamForm.getName());
		team.setDescription(teamForm.getDescription());

		Team newTeam = teamRepository.save(team);

		// ユーザのプロキシを取得
		User user = userRepository.getReferenceById(userId);

		// 中間テーブルに保存
		UserTeam userTeam = new UserTeam();
		userTeam.setUser(user);
		userTeam.setTeam(newTeam);

		userTeamRepository.save(userTeam);
	}

	public void updateTeam(TeamEditForm teamEditForm) {
		// 名前が変更される場合にチーム名がすでに存在するか確認
		if (teamRepository.existsByNameAndNotTeamId(teamEditForm.getName(), teamEditForm.getTeamId())) {
			throw new IllegalArgumentException(ErrorMessages.TEAM_NAME_ALREADY_EXISTS);
		}

		// TeamEditForm を TeamEntity に変換
		Team team = new Team();
		team.setTeamId(teamEditForm.getTeamId());
		team.setName(teamEditForm.getName());
		team.setDescription(teamEditForm.getDescription());

		teamRepository.save(team);
	}

	public boolean deleteTeam(int teamId) {
		// teamId の存在確認
		if (teamRepository.existsByTeamId(teamId)) {
			teamRepository.deleteById(teamId);

			return true;
		} else {
			return false;
		}
	}
}