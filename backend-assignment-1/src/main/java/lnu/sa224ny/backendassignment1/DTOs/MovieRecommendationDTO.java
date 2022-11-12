package lnu.sa224ny.backendassignment1.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class MovieRecommendationDTO {
    private String title;
    private int id;
    private double score;
}
