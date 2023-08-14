package tr.org.ji.pokedex.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequestDTO {
    @NotBlank(message = "Please new enter password")
    @Size(min = 4, max = 256, message = "Please enter valid password")
    private String newPassword;

    @NotBlank(message = "Please old enter password")
    @Size(min = 4, max = 256, message = "Please enter valid password")
    private String oldPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
