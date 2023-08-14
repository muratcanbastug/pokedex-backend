package tr.org.ji.pokedex.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "USER_ACCOUNT")
public class User extends EntityBase{
    @Column(name = "USERNAME", length = 255, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 255)
    private String password;

    @ManyToMany
    @JoinTable(name = "USER_ROLES",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name = "WISH_LIST",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "POKEMON_ID", referencedColumnName = "ID")})
    private Set<Pokemon> wishList;

    @ManyToMany
    @JoinTable(name = "CATCH_LIST",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "POKEMON_ID", referencedColumnName = "ID")})
    private Set<Pokemon> catchList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Pokemon> getWishList() {
        return wishList;
    }

    public void setWishList(Set<Pokemon> wishList) {
        this.wishList = wishList;
    }

    public Set<Pokemon> getCatchList() {
        return catchList;
    }

    public void setCatchList(Set<Pokemon> catchList) {
        this.catchList = catchList;
    }
}
