package lnu.sa224ny.backendassignment1.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RecommendationRequestDTO {
    private String userName;
    private String method;
    private String similarity;
    private int count;
}
