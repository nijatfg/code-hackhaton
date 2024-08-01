package az.code.codehackathon.repository;

import az.code.codehackathon.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
