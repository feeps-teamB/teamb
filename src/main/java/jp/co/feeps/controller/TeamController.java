package jp.co.feeps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.dto.TeamSelectDTO;
import jp.co.feeps.service.TeamService;

@RestController
@CrossOrigin
public class TeamController {
	@Autowired
	private TeamService teamService;

	// GET http://localhost:8080/teamSelect/view/{userId}
	@GetMapping("/teamSelect/view/{userId}")
	public ResponseEntity<List<TeamSelectDTO>> getTeams(@PathVariable int userId) {
		try {
			List<TeamSelectDTO> TeamSelectDTOs = teamService.getTeamsByUserId(userId);

			// ステータス: 200 OK
			// ボディ：ユーザが参加しているチーム一覧
			return ResponseEntity.status(HttpStatus.OK).body(TeamSelectDTOs);
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
