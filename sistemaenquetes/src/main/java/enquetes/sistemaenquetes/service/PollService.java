package enquetes.sistemaenquetes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import enquetes.sistemaenquetes.dto.OptionResponseDTO;
import enquetes.sistemaenquetes.dto.PollRequestDTO;
import enquetes.sistemaenquetes.dto.PollResponseDTO;
import enquetes.sistemaenquetes.enums.PollStatus;
import enquetes.sistemaenquetes.exception.InvalidPollDateException;
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
	    private final OptionRepository optionRepository; // Embora as opções sejam cascateadas, pode ser útil
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
	            newPoll.addOption(new Option(optionText, newPoll)); // Passa newPoll para a bidirecionalidade
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
	    
	    
}
