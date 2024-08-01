package az.code.codehackathon.controllor;

import az.code.codehackathon.dto.request.JuryStaffRequest;
import az.code.codehackathon.dto.request.TechnicalStaffRequest;
import az.code.codehackathon.dto.response.JuryStaffResponse;
import az.code.codehackathon.dto.response.StudentResponse;
import az.code.codehackathon.dto.response.TechnicalStaffResponse;
import az.code.codehackathon.model.Team;
import az.code.codehackathon.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        log.info("createTeam controller: " + createdTeam);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @PutMapping("/{teamId}/technical-score")
    public ResponseEntity<Team> updateTechnicalStaffScore(@PathVariable Long teamId, @RequestBody TechnicalStaffRequest request) {
        Team updatedTeam = teamService.updateTechnicalStaffScore(teamId, request);
        log.info("updateTechnicalStaffScore controller: " + updatedTeam);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @PutMapping("/{teamId}/jury-score")
    public ResponseEntity<Team> updateJuryScore(@PathVariable Long teamId, @RequestBody JuryStaffRequest request) {
        Team updatedTeam = teamService.updateJuryScore(teamId, request);
        log.info("updateJuryScore controller: " + updatedTeam);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @GetMapping("/technical-scores")
    public ResponseEntity<List<TechnicalStaffResponse>> getAllTechnicalScores() {
        List<TechnicalStaffResponse> technicalScores = teamService.getAllTechnicalScores();
        log.info("getAllTechnicalScores controller: " + technicalScores);
        return new ResponseEntity<>(technicalScores, HttpStatus.OK);
    }

    @GetMapping("/jury-scores")
    public ResponseEntity<List<JuryStaffResponse>> getAllJuryScores() {
        List<JuryStaffResponse> juryScores = teamService.getAllJuryScores();
        log.info("getAllJuryScores controller: " + juryScores);
        return new ResponseEntity<>(juryScores, HttpStatus.OK);
    }

    @GetMapping("/all-scores")
    public ResponseEntity<List<StudentResponse>> getAllScores() {
        List<StudentResponse> allScores = teamService.getAllScores();
        log.info("getAllScores controller: " + allScores);
        return new ResponseEntity<>(allScores, HttpStatus.OK);
    }
}
