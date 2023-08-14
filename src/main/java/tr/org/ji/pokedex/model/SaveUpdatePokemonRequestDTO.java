package tr.org.ji.pokedex.model;

import jakarta.validation.constraints.*;

import java.util.List;

public class SaveUpdatePokemonRequestDTO {
    @NotBlank(message = "Please enter name")
    private String name;

    @Size(max = 256, message = "Please enter valid description")
    private String description;

    private List<String> types;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    private String difficulty;

    @Min(0)
    @Max(5)
    private float offense;

    @Min(0)
    @Max(5)
    private float support;

    @Min(0)
    @Max(5)
    private float scoring;

    @Min(0)
    @Max(5)
    private float mobility;

    @Min(0)
    @Max(5)
    private float endurance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }



    public float getOffense() {
        return offense;
    }

    public void setOffense(float offense) {
        this.offense = offense;
    }

    public float getSupport() {
        return support;
    }

    public void setSupport(float support) {
        this.support = support;
    }

    public float getScoring() {
        return scoring;
    }

    public void setScoring(float scoring) {
        this.scoring = scoring;
    }

    public float getMobility() {
        return mobility;
    }

    public void setMobility(float mobility) {
        this.mobility = mobility;
    }

    public float getEndurance() {
        return endurance;
    }

    public void setEndurance(float endurance) {
        this.endurance = endurance;
    }
}
