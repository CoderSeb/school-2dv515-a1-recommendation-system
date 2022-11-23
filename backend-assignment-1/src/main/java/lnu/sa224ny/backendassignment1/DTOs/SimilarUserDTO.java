package lnu.sa224ny.backendassignment1.DTOs;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class SimilarUserDTO {
    private String name = null;
    private int id = 0;
    private double score = 0;
}
