package az.code.codehackathon.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;
    private String name;
    private Double technicalStaffScore;
    private Double juryScore;
    private Double averageScore;
    private Integer technicalStaffVoteCount;
    private Integer juryVoteCount;


    public Team(String name, double technicalStaffScore, double juryScore, int technicalStaffVoteCount, int juryVoteCount) {
        this.name = name;
        this.technicalStaffScore = technicalStaffScore;
        this.juryScore = juryScore;
        this.technicalStaffVoteCount = technicalStaffVoteCount;
        this.juryVoteCount = juryVoteCount;
//        this.averageScore = calculateAverageScore(technicalStaffScore, juryScore);
    }

    public Team() {

    }
}
