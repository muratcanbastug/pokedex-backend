package tr.org.ji.pokedex.model;

import java.util.Set;

public class UserResponseDTO {
    private String name;
    private long id;
    private Set<String> roles;
    private Set<String> wishList;
    private Set<String> catchList;

    public Set<String> getWishList() {
        return wishList;
    }

    public void setWishList(Set<String> wishList) {
        this.wishList = wishList;
    }

    public Set<String> getCatchList() {
        return catchList;
    }

    public void setCatchList(Set<String> catchList) {
        this.catchList = catchList;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

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

}
