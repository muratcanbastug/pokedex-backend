package tr.org.ji.pokedex.model;

import tr.org.ji.pokedex.entity.PokemonType;

import java.util.Set;

public class PokemonResponseDTO {
    private String name;
    private String description;
    private Set<String> types;
    private String difficulty;
    private float offense;
    private float support;
    private float scoring;
    private float mobility;
    private float endurance;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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
