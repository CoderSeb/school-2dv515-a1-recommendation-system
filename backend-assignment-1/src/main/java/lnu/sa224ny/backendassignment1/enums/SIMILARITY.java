package lnu.sa224ny.backendassignment1.dtos;

public enum SIMILARITY {
    Pearson("Pearson"),
    Euclidean("Euclidean");

    public final String label;

    SIMILARITY(String label) {
        this.label = label;
    }
}
