package az.code.codehackathon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;

    private String imageUrl;
    private String name;
    private double technicalStaffScore;
    private double juryScore;
    private double averageScore;
}
