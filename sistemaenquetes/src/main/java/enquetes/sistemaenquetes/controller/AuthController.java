package enquetes.sistemaenquetes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enquetes.sistemaenquetes.dto.LoginResponseDTO;
import enquetes.sistemaenquetes.dto.UserLoginDTO;
import enquetes.sistemaenquetes.dto.UserRegistrationDTO;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.service.AuthService;
import jakarta.validation.Valid;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/v1/auth") // Define o caminho base para todos os endpoints neste controlador
public class AuthController {
	
	private final AuthService authService ;

	public AuthController(AuthService authService) {
		
		this.authService = authService;
	} 
	
	@PostMapping("/register")
	public  ResponseEntity<User> register ( @RequestBody @Valid UserRegistrationDTO userRegistrationDTO){
		
		User userNew  =  authService.registerUser(userRegistrationDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userNew);
	}
    @PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login ( @RequestBody @Valid UserLoginDTO userLoginDTO ){
		
		LoginResponseDTO login = authService.loginResponseDTO(userLoginDTO);
		return  ResponseEntity.ok(login);
	}
	
	
	
}
