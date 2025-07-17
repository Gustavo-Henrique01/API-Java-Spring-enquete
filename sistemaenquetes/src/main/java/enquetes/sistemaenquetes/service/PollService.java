package enquetes.sistemaenquetes.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import enquetes.sistemaenquetes.dto.OptionResponseDTO;
import enquetes.sistemaenquetes.dto.PollRequestDTO;
import enquetes.sistemaenquetes.dto.PollResponseDTO;
import enquetes.sistemaenquetes.enums.PollStatus;
import enquetes.sistemaenquetes.enums.UserRole;
import enquetes.sistemaenquetes.exception.InvalidPollDateException;
import enquetes.sistemaenquetes.exception.ResourceNotFoundException;
import enquetes.sistemaenquetes.model.Option;
import enquetes.sistemaenquetes.model.Poll;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.repository.OptionRepository;
import enquetes.sistemaenquetes.repository.PollRepository;
import enquetes.sistemaenquetes.repository.VoteRepository;
import java.util.stream.Collectors;


@Service
public class PollService {

	
	 private final PollRepository pollRepository;
	    private final OptionRepository optionRepository; 
	    private final VoteRepository voteRepository; // Para obter a contagem de votos
	    private final UserService userService; // Para buscar o usuário criador
	    
		public PollService(PollRepository pollRepository, OptionRepository optionRepository,
				VoteRepository voteRepository, UserService userService) {
			
			this.pollRepository = pollRepository;
			this.optionRepository = optionRepository;
			this.voteRepository = voteRepository;
			this.userService = userService;
		}
		
		@Transactional
	    public PollResponseDTO createPoll(PollRequestDTO pollDTO, Long creatorUserId) {
	        // 1. Validações de Regra de Negócio:
	        if (pollDTO.getStartDate().isAfter(pollDTO.getEndDate())) {
	            throw new InvalidPollDateException("A data de início da enquete não pode ser posterior à data de término.");
	        }
	        if (pollDTO.getOptions() == null || pollDTO.getOptions().size() < 2) {
	            throw new IllegalArgumentException("A enquete deve ter pelo menos duas opções.");
	        }

	        // 2. Busca o usuário criador
	        User creator = userService.getUserEntityById(creatorUserId);

	        // 3. Cria a entidade Poll
	        Poll newPoll = new Poll(
	            pollDTO.getQuestion(),
	            pollDTO.getDescription(),
	            pollDTO.getStartDate(),
	            pollDTO.getEndDate(),
	            PollStatus.INACTIVE, // Enquetes começam como DRAFT
	            creator
	        );

	        // 4. Adiciona as opções à enquete
	        for (String optionText : pollDTO.getOptions()) { 
	            newPoll.addOption(new Option(optionText, newPoll));
	        }

	        // 5. Salva a enquete e suas opções (graças ao CascadeType.ALL)
	        Poll savedPoll = pollRepository.save(newPoll);

	        // 6. Converte para PollResponseDTO
	        List<OptionResponseDTO> optionDTOs = savedPoll.getOptions().stream()
	                .map(option -> new OptionResponseDTO(option.getId(), option.getText(), 0L)) // Votos serão 0 na criação
	                .collect(Collectors.toList());

	        return new PollResponseDTO(
	            savedPoll.getId(),
	            savedPoll.getQuestion(),
	            savedPoll.getDescription(),
	            savedPoll.getStartDate(),
	            savedPoll.getEndDate(),
	            savedPoll.getStatus(),
	            savedPoll.getCreatedBy().getUsername(),
	            optionDTOs
	        );
	    }
		
		@Transactional(readOnly = true)
		  public PollResponseDTO getPollById(Long pollId) {
			  Poll  poll = pollRepository.findById(pollId).
			orElseThrow(() -> new ResourceNotFoundException("Enquete não encontrada com ID: " + pollId));
			  
		        // Mapeia opções para DTOs, incluindo a contagem de votos

			 List <OptionResponseDTO> optionResponseDto = poll.getOptions().stream().
					 map(option -> {
						 long votCount = voteRepository.countByOptionId(option.getId());
						 return  new OptionResponseDTO(option.getId(), option.getText() , votCount);
					 }).collect(Collectors.toList());

			 return new PollResponseDTO(
			            poll.getId(),
			            poll.getQuestion(),
			            poll.getDescription(),
			            poll.getStartDate(),
			            poll.getEndDate(),
			            poll.getStatus(),
			            poll.getCreatedBy().getUsername(),
			            optionResponseDto
			        );
		  
		  }
		
		  @Transactional(readOnly = true)
		    public List<PollResponseDTO> getAllPolls(PollStatus status) {
			  
			  List <Poll> polls ;
			  
			  if( status != null) {
				  polls = pollRepository.findByStatus(status);
			  }
			  else {
				  polls = pollRepository.findAll();
			  }
			  
			  return polls.stream().
					  map(poll -> {
						  List<OptionResponseDTO> optionsDTOs = poll.getOptions().stream().
								  map(option -> new OptionResponseDTO(option.getId()
										  , option.getText(), voteRepository.countByOptionId(option.getId()))).
								  collect(Collectors.toList());
						  
						  return new PollResponseDTO(
			                        poll.getId(),
			                        poll.getQuestion(),
			                        poll.getDescription(),
			                        poll.getStartDate(),
			                        poll.getEndDate(),
			                        poll.getStatus(),
			                        poll.getCreatedBy().getUsername(),
			                        optionsDTOs
			                    );
					  }).collect(Collectors.toList());
		  }
		  
		  @Transactional
		    public PollResponseDTO updatePollStatus(Long pollId, PollStatus newStatus, Long userId) {
			  Poll poll = pollRepository.findById(pollId).orElseThrow(()->
			  new ResourceNotFoundException("Enquete não encontrada"));
			  
			// Validação de autorização: apenas o criador ou ADMIN pode alterar o status
			  
			  if(!poll.getCreatedBy().getId().equals(userId) && !userService.getUserEntityById(userId).getRole().
				equals(UserRole.ROLE_ADMIN)	  ) {
				  
				  throw new SecurityException("Usuário não autorizado para alterar o status desta enquete.");
				  
			  }
		        // Validações de transição de status (ex: não pode ir de CLOSED para ACTIVE)

			  if(poll.getStatus() == PollStatus.CLOSED && newStatus == PollStatus.ACTIVE) {
				  
		            throw new IllegalArgumentException("Não é possível reabrir uma enquete já fechada.");

			  }
			  
			  poll.setStatus(newStatus);
		        Poll updatedPoll = pollRepository.save(poll);

		        // Retorna o DTO atualizado
		        return getPollById(updatedPoll.getId()); // Reutiliza o método para construir o DTO completo
		    }
		  
		  @Transactional
		    public void deletePoll(Long pollId, Long userId) {
			  
			  Poll poll = pollRepository.findById(pollId).orElseThrow(()->
			  new ResourceNotFoundException("Enquete não encontrada"));
			  
			  // Validação de autorização: apenas o criador ou ADMIN pode excluir
		        if (!poll.getCreatedBy().getId().equals(userId) && !userService.getUserEntityById(userId).getRole().equals(UserRole.ROLE_ADMIN)) {
		            throw new SecurityException("Usuário não autorizado para excluir esta enquete.");
		        }
		        if(poll.getStatus() ==  PollStatus.CLOSED || poll.getStatus() ==  PollStatus.ACTIVE) {
		        	throw new IllegalArgumentException("Não é possível excluir enquetes ativas ou encerradas.");
		        }
		        
		        pollRepository.delete(poll);
		  }
		  
		
		
	    
	    
}
