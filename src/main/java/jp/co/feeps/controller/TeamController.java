package jp.co.feeps.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jp.co.feeps.dto.TeamDTO;
import jp.co.feeps.dto.TeamSelectDTO;
import jp.co.feeps.form.TeamEditForm;
import jp.co.feeps.form.TeamForm;
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

	// POST http://localhost:8080/addTeamSave/{userId}
	@PostMapping("/addTeamSave/{userId}")
	public ResponseEntity<String> create(@PathVariable int userId, @RequestBody TeamForm teamForm) {
		try {
			teamService.saveTeam(userId, teamForm);

			// ステータス: 201 Created
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (IllegalArgumentException error) {
			// ステータス: 409 CONFLICT
			// ボディ: チーム重複のメッセージ
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage());
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// GET http://localhost:8080/teamsDetail/view/{teamId}
	@GetMapping("/teamsDetail/view/{teamId}")
	public ResponseEntity<TeamDTO> show(@PathVariable int teamId) {
		try {
			Optional<TeamDTO> teamDTOOpt = teamService.getTeam(teamId);

			// Optional を用いて空の場合の処理分けを行う
			if (teamDTOOpt.isPresent()) {
				// ステータス: 200 OK
				// ボディ: 選択したチーム
				return ResponseEntity.ok(teamDTOOpt.get());
			} else {
				// ステータス: 404 Not Found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// PUT http://localhost:8080/teamEdit
	@PutMapping("/teamEdit")
	public ResponseEntity<String> update(@RequestBody TeamEditForm teamEditForm) {
		try {
			teamService.updateTeam(teamEditForm);

			// ステータス: 200 OK
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException error) {
			// ステータス: 409 CONFLICT
			// ボディ: チーム重複のメッセージ
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage());
		} catch (Exception error) {
			// ステータス: 500 Internal Server Error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}