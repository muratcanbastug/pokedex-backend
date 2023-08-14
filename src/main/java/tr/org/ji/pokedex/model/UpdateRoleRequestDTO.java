package tr.org.ji.pokedex.model;

import java.util.List;

public class UpdateRoleRequestDTO {
    private List<String> newRoles;

    public List<String> getNewRoles() {
        return newRoles;
    }

    public void setNewRoles(List<String> newRoles) {
        this.newRoles = newRoles;
    }
}
