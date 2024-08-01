package az.code.codehackathon.service;

import az.code.codehackathon.dto.request.JuryStaffRequest;
import az.code.codehackathon.dto.request.TechnicalStaffRequest;
import az.code.codehackathon.dto.response.JuryStaffResponse;
import az.code.codehackathon.dto.response.StudentResponse;
import az.code.codehackathon.dto.response.TechnicalStaffResponse;
import az.code.codehackathon.exception.ErrorCodes;
import az.code.codehackathon.exception.JuryStaffScoreException;
import az.code.codehackathon.exception.TeamNotFoundException;
import az.code.codehackathon.exception.TechnicalStaffScoreException;
import az.code.codehackathon.model.Team;
import az.code.codehackathon.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public Team createTeam(Team team) {
        team.setAverageScore(calculateAverageScore(team.getTechnicalStaffScore(), team.getJuryScore()));
        log.info("create team service: " + team);
        return teamRepository.save(team);
    }

    public Team updateTechnicalStaffScore(Long teamId, TechnicalStaffRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException(ErrorCodes.TEAM_NOT_FOUND));

        if (request.getTechnicalStaffScore() < 0 || request.getTechnicalStaffScore() > 100) {
            throw new TechnicalStaffScoreException(ErrorCodes.TECHNICAL_STAFF_SCORE);
        }

        // Initialize technicalStaffVoteCount to 0 if null
        Integer technicalStaffVoteCount = team.getTechnicalStaffVoteCount() != null ? team.getTechnicalStaffVoteCount() : 0;

        int newVoteCount = technicalStaffVoteCount + 1;

        // Initialize technicalStaffScore to 0.0 if null
        Double technicalStaffScore = team.getTechnicalStaffScore() != null ? team.getTechnicalStaffScore() : 0.0;

        double currentTechnicalStaffScore = technicalStaffScore * technicalStaffVoteCount;
        double newTechnicalStaffScore = (currentTechnicalStaffScore + request.getTechnicalStaffScore()) / newVoteCount;

        team.setTechnicalStaffVoteCount(newVoteCount);
        team.setTechnicalStaffScore(newTechnicalStaffScore);

        Double juryScore = team.getJuryScore() != null ? team.getJuryScore() : 0.0;
        team.setAverageScore(calculateAverageScore(newTechnicalStaffScore, juryScore));

        log.info("updateTechnicalStaffScore service: " + team);
        return teamRepository.save(team);
    }


    public Team updateJuryScore(Long teamId, JuryStaffRequest request) {
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new TeamNotFoundException(ErrorCodes.TEAM_NOT_FOUND));

        if (request.getJuryScore() < 0 || request.getJuryScore() > 5) {
            throw new JuryStaffScoreException(ErrorCodes.JURY_STAFF_SCORE);
        }

        int currentVoteCount = team.getJuryVoteCount() != null ? team.getJuryVoteCount() : 0;
        int newVoteCount = currentVoteCount + 1;

        double currentJuryScore = team.getJuryScore() != null ? team.getJuryScore() : 0.0;
        double newJuryScore = (currentJuryScore * currentVoteCount + request.getJuryScore()) / newVoteCount;

        team.setJuryVoteCount(newVoteCount);
        team.setJuryScore(newJuryScore);

        Double technicalStaffScore = team.getTechnicalStaffScore() != null ? team.getTechnicalStaffScore() : 0.0;

        // Update the team's average score
        team.setAverageScore(calculateAverageScore(technicalStaffScore, newJuryScore));

        log.info("updateJuryScore service: " + team);
        return teamRepository.save(team);
    }


    public List<TechnicalStaffResponse> getAllTechnicalScores() {
        log.info("getAllTechnicalScores service: ");
        return teamRepository.findAll().stream()
                .map(team -> modelMapper.map(team, TechnicalStaffResponse.class))
                .collect(Collectors.toList());
    }

    public List<JuryStaffResponse> getAllJuryScores() {
        log.info("getAllJuryScores service: ");
        return teamRepository.findAll().stream()
                .map(team -> modelMapper.map(team, JuryStaffResponse.class))
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getAllScores() {
        log.info("getAllScores service: ");
        return teamRepository
                .findAll()
                .stream()
                .map(team -> modelMapper.map(team, StudentResponse.class))
                .collect(Collectors.toList());
    }

    public double calculateAverageScore(double technicalStaffScore, double juryScore) {
        double normalizedJuryScore = juryScore * 20;

        double sumOfScores = technicalStaffScore + normalizedJuryScore;

        return sumOfScores / 2;
    }
}
