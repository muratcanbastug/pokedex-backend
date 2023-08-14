package tr.org.ji.pokedex.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "POKEMON")
public class Pokemon extends EntityBase{
    @Column(name = "NAME", length = 255, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 255)
    private String description;

    @ManyToMany(mappedBy = "wishList")
    private Set<User> wishingUsers;

    @ManyToMany(mappedBy = "catchList")
    private Set<User> catchingUsers;

    @ManyToMany
    @JoinTable(name = "POKEMON_TYPES",
            joinColumns = {@JoinColumn(name = "POKEMON_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "TYPE_ID", referencedColumnName = "ID")})
    private Set<PokemonType> types;

    @Column(name = "DIFFICULTY")
    private String difficulty;

    @Min(0)
    @Max(5)
    @Column(name = "OFFENSE")
    private float offense;

    @Min(0)
    @Max(5)
    @Column(name = "SUPPORT")
    private float support;

    @Min(0)
    @Max(5)
    @Column(name = "SCORING")
    private float scoring;

    @Min(0)
    @Max(5)
    @Column(name = "MOBILITY")
    private float mobility;

    @Min(0)
    @Max(5)
    @Column(name = "ENDURANCE")
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

    public Set<User> getWishingUsers() {
        return wishingUsers;
    }

    public void setWishingUsers(Set<User> wishingUsers) {
        this.wishingUsers = wishingUsers;
    }

    public Set<User> getCatchingUsers() {
        return catchingUsers;
    }

    public void setCatchingUsers(Set<User> catchingUsers) {
        this.catchingUsers = catchingUsers;
    }

    public Set<PokemonType> getTypes() {
        return types;
    }

    public void setTypes(Set<PokemonType> types) {
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
