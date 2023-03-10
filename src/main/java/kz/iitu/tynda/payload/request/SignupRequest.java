package kz.iitu.tynda.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(
            min = 3,
            max = 20,
            message = "Username '${validatedValue}' must be between {min} and {max} characters long")
    private String username;

    @NotBlank
    @Size(
            min = 3,
            max = 50,
            message = "The author email '${validatedValue}' must be between {min} and {max} characters long")
    @Email (message = "Неправильный формат адреса электронной почты")
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(
            min = 6,
            max = 40,
            message = "Password must be between {min} and {max} characters long"
    )
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
