package enquetes.sistemaenquetes.dto;
//Para retornar o JWT após o login bem-sucedido.
public class LoginResponseDTO {
    private String token;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}