package az.code.codehackathon.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleTeamNotFoundException(TeamNotFoundException ex,
                                                                      WebRequest req) {

        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Team not found.")
                .build());
    }

    @ExceptionHandler(TechnicalStaffScoreException.class)
    public ResponseEntity<ErrorResponseDto> handleTechnicalStaffScoreException(TechnicalStaffScoreException ex,
                                                                      WebRequest req) {

        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Technical staff score must be between 0 and 100.")
                .build());
    }

    @ExceptionHandler(JuryStaffScoreException.class)
    public ResponseEntity<ErrorResponseDto> handleJuryStaffScoreException(JuryStaffScoreException ex,
                                                                               WebRequest req) {

        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Jury score must be between 0 and 5.")
                .build());
    }

}
