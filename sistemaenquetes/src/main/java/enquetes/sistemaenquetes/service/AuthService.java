package enquetes.sistemaenquetes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import enquetes.sistemaenquetes.dto.UserRegistrationDTO;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.repository.UserRepository;

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
	    		 throw new UsernameAlreadyExistsException("O nome de usuário '" + registrationDTO.getUsername() + "' já está em uso.");
	    	}
	    }

}
