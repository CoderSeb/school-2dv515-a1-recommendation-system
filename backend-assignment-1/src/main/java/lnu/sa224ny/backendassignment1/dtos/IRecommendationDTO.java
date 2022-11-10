package lnu.sa224ny.backendassignment1.dtos;

import lnu.sa224ny.backendassignment1.models.User;

public interface IRecommendationDTO {
    User user = null;
    METHOD Method = METHOD.ItemBased;
    SIMILARITY Similarity = SIMILARITY.Euclidean;
}
