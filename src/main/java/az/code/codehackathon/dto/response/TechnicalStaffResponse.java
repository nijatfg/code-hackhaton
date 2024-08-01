package az.code.codehackathon.dto.response;

import lombok.Data;

@Data
public class TechnicalStaffResponse {
    private Long id;
    private String imageUrl;
    private String name;
    private double technicalStaffScore;
}
