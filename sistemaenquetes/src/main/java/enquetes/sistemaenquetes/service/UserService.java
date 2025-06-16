package enquetes.sistemaenquetes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import enquetes.sistemaenquetes.dto.UserProfileDTO;
import enquetes.sistemaenquetes.exception.ResourceNotFoundException;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	
	  public UserService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
	  
	  @Transactional(readOnly = true)
	  public UserProfileDTO getPerfilUser (Long userId) {
		  
		  User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(
				  "Usuário não encontrado com ID: " + userId)  ) ;
		  
		  return new UserProfileDTO(
		            user.getId(),
		            user.getUsername(),
		            user.getRole(),
		            user.getCreatedAt(),
		            user.getUpdatedAt()
		        );
	  }
	  
	  public User getUserEntityById (Long userId) {
		  
		  
		  return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));
	  }
	  
	  
}
