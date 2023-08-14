package tr.org.ji.pokedex.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SaveUserRequestDTO {
    @NotBlank(message = "Please enter name")
    @Size(min = 4, max = 256, message = "Please enter valid name")
    @Email
    private String name;

    @NotBlank(message = "Please enter password")
    @Size(min = 4, max = 256, message = "Please enter valid password")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
