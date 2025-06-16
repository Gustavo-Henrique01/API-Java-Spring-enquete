package enquetes.sistemaenquetes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


//Para receber dados de registro de um novo usuário
public class UserRegistrationDTO {

    @NotBlank(message = "O username é obrigatório.")
    @Size(min = 3, max = 50, message = "O username deve ter entre 3 e 50 caracteres.")
    private String username;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;

    @NotBlank(message = "A role é obrigatória.")
    private String role; // Pode ser mapeado para UserRole no serviço

    public UserRegistrationDTO() {}

    public UserRegistrationDTO(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters e Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}