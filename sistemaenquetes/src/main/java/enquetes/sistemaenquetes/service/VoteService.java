package enquetes.sistemaenquetes.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import enquetes.sistemaenquetes.dto.VoteRequestDTO;
import enquetes.sistemaenquetes.dto.VoteResponseDTO;
import enquetes.sistemaenquetes.enums.PollStatus;
import enquetes.sistemaenquetes.exception.PollNotActiveException;
import enquetes.sistemaenquetes.exception.ResourceNotFoundException;
import enquetes.sistemaenquetes.exception.UserAlreadyVotedException;
import enquetes.sistemaenquetes.model.Poll;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.model.Vote;
import enquetes.sistemaenquetes.model.Option;

import enquetes.sistemaenquetes.repository.OptionRepository;
import enquetes.sistemaenquetes.repository.PollRepository;
import enquetes.sistemaenquetes.repository.UserRepository;
import enquetes.sistemaenquetes.repository.VoteRepository;

@Service
public class VoteService {
	
	private final VoteRepository voteRepository; 
	private final UserRepository userRepository ;
	private final PollRepository pollRepository;
	private final OptionRepository optionRepository ;
	
	public VoteService(VoteRepository voteRepository, UserRepository userRepository, PollRepository pollRepository,
			OptionRepository optionRepository) {
		
		this.voteRepository = voteRepository;
		this.userRepository = userRepository;
		this.pollRepository = pollRepository;
		this.optionRepository = optionRepository;
	}
	
	@Transactional
	public VoteResponseDTO registerVote (VoteRequestDTO voteRequestDTO , Long userId  ) {
		
		//buscar usuario existente
		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + userId));
		
		// Busca a enquete e a opção
		
		Poll poll = pollRepository.findById(voteRequestDTO.getPollId()).orElseThrow(()->
		new ResourceNotFoundException("Enque não encontrada com o ID: "+voteRequestDTO.getPollId()));
		
		Option option = optionRepository.findById(voteRequestDTO.getOptionId()).
        orElseThrow(() -> new ResourceNotFoundException("Opção de voto não en0contrada com ID: " + voteRequestDTO.getOptionId()));
		
		
        // A opção realmente pertence à enquete correta?

		if(!option.getPoll().getId().equals(poll.getId())) {
			
			throw new IllegalArgumentException("A opção informada não pertence a enquete informada");
		}
		
        // a enquete está ativa e dentro do período de votação?
		
		LocalDateTime now = LocalDateTime.now();
		
		if(poll.getStatus() != PollStatus.ACTIVE || now.isBefore(poll.getStartDate())) {
			throw new PollNotActiveException("Está enqute não está ativa para votação"); 
			
		}
        // verificar se o usuário já votou nesta enquete? (Usando a validação de unicidade do VoteRepository/Entidade)
		
		if(voteRepository.findByUserAndPoll(user, poll).isPresent()) {
		 throw new UserAlreadyVotedException("Você ja votou nessa enquete");
		}
		//cria a etidade vote
		Vote newVote = new Vote(user,option,poll);
		
		//salva voto
		
		Vote voteSave = voteRepository.save(newVote);
		
		// Converte para VoteResponseDTO
        return new VoteResponseDTO(
            voteSave.getId(),
            voteSave.getUser().getUsername(),
            voteSave.getOption().getText(),
            voteSave.getVotedAT()
        );
		
	}
	@Transactional(readOnly = true) 
	public long getCountForOption ( Long optionId) {
		//verificar se exixte 
		Option option = optionRepository.findById(optionId).
				orElseThrow(( ) -> new ResourceNotFoundException("Opção de voto não encontrada  com o ID:  "+optionId));
		//retornar a contagem para a opção de voto
		return voteRepository.countByOptionId(optionId);
	}
	
	@Transactional (readOnly = true)
	public long getTotalVotes ( Long pollId ) {
		
        //  Verifica se a enquete existe
		
		Poll poll = pollRepository.findById(pollId).
				orElseThrow(()-> new ResourceNotFoundException("Enquete não encontrada com o id: "+pollId));
        //  Retorna a contagem total de votos para a enquete

		return  voteRepository.countByPollId(pollId) ;
	}

}
