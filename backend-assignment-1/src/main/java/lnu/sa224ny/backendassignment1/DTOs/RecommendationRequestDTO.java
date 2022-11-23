package lnu.sa224ny.backendassignment1.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RecommendationRequestDTO {
    private int userId;
    private String method;
    private String similarity;
    private int count;
}
