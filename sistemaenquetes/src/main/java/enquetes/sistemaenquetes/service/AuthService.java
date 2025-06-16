package enquetes.sistemaenquetes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import enquetes.sistemaenquetes.repository.UserRepository;

@Service
public class AuthService {
	
	  private final UserRepository userRepository;
	    private final PasswordEncoder passwordEncoder;
	//    private final JwtService jwtService;
	    private final AuthenticationManager authenticationManager;

}
