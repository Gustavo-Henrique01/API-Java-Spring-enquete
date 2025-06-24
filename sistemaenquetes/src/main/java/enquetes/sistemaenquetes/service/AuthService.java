package enquetes.sistemaenquetes.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;

import enquetes.sistemaenquetes.config.JwtService;
import enquetes.sistemaenquetes.dto.LoginResponseDTO;
import enquetes.sistemaenquetes.dto.UserLoginDTO;
import enquetes.sistemaenquetes.dto.UserRegistrationDTO;
import enquetes.sistemaenquetes.enums.UserRole;
import enquetes.sistemaenquetes.exception.UsernameAlreadyExistsException;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@Service
public class AuthService {
	
	 	private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final AuthenticationManager authenticationManager;
	    private final JwtService jwtService;
	    
	    // Injeção de dependências via construtor ()
	    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
	                       AuthenticationManager authenticationManager, JwtService jwtService) {
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.authenticationManager = authenticationManager;
	        this.jwtService = jwtService;
	    }
	    
	    public User registerUser (UserRegistrationDTO userResgisterDTO ) {
	    	
	    	if(userRepository.findByUsername(userResgisterDTO.getUsername()).isPresent()) {
	    		 throw new UsernameAlreadyExistsException("O nome de usuário '" + userResgisterDTO.getUsername() + "' já está em uso.");
	    	}
	    	 UserRole role;
	    	 try {
				role = UserRole.valueOf(userResgisterDTO.getRole().toUpperCase());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("Role inválida: " + userResgisterDTO.getRole());
			}
	    	 
	    	 String encryptedPassword  = passwordEncoder.encode(userResgisterDTO.getPassword());
	    	 
	    	 User newUser = new User(
	    	            userResgisterDTO.getUsername(),
	    	            encryptedPassword,
	    	            role
	    	        );
	     return	 userRepository.save(newUser);
	    	 
	    }
	    @Transactional(readOnly = true)
	    public LoginResponseDTO loginResponseDTO (UserLoginDTO userLoginDTO) {
	    	
	    	//  Autentica o usuário usando o Spring Security AuthenticationManager
	        // Isso vai verificar as credenciais e, se corretas, criar um objeto Authentication
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
	        );
	        
	        //  Se a autenticação for bem-sucedida, gera o JWT para o usuário autenticado
	        // O authentication.getName() retorna o username do usuário autenticado
	        String jwtToken = jwtService.generateToken(authentication.getName());

	        //  Retorna o token em um DTO de resposta
	        return new LoginResponseDTO(jwtToken);
	    }

}
