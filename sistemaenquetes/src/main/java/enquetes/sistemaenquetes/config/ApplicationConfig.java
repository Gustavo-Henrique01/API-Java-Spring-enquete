package enquetes.sistemaenquetes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import enquetes.sistemaenquetes.repository.UserRepository;

@Configuration
public class ApplicationConfig {
	
	
	private final UserRepository userRepository ;

	public ApplicationConfig(UserRepository userRepository) {
		
		this.userRepository = userRepository;
	} 
	
    @Bean

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }
    

    @Bean
	public PasswordEncoder passwordEncoder () {
		
		return new BCryptPasswordEncoder() ;
	}
	@Bean
	public AuthenticationProvider   authenticationProvider() { 
		
		DaoAuthenticationProvider authprovider = new DaoAuthenticationProvider();
		
		authprovider.setUserDetailsService(userDetailsService());
		authprovider.setPasswordEncoder(passwordEncoder());
		return authprovider;
	}
	@Bean
	public AuthenticationManager  authenticarionManager  ( AuthenticationConfiguration config ) throws Exception {
		return config.getAuthenticationManager();
	}
	

}
