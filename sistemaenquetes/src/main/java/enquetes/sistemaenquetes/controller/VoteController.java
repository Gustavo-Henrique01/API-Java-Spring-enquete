package enquetes.sistemaenquetes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enquetes.sistemaenquetes.dto.VoteRequestDTO;
import enquetes.sistemaenquetes.dto.VoteResponseDTO;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.service.VoteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {
	
	private final VoteService voteService ;

	public VoteController(VoteService voteService) {
		this.voteService = voteService;
	}
	@PostMapping
	@PreAuthorize("hasAnyRole('CREATOR', 'ADMIN','VOTER')")
	public ResponseEntity<VoteResponseDTO> registerVote (@RequestBody @Valid VoteRequestDTO voteRequestDTO){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userAuth =  (User) authentication.getPrincipal();
		Long userid = userAuth.getId();
		
		VoteResponseDTO registerVote  = voteService.registerVote(voteRequestDTO, userid);
		return ResponseEntity.status(HttpStatus.CREATED).body(registerVote);
	}
	@GetMapping("/count/option/{optionId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Long> getVoteCountForOption (@PathVariable Long optionId ) {
		
		long VoteCount = voteService.getCountForOption(optionId);
		return ResponseEntity.ok(VoteCount) ; 
	}
	
	@GetMapping("/count/poll/{pollId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Long> getTotalVotesForPoll (@PathVariable Long pollId) {
		long totalVotes = voteService.getTotalVotes(pollId);
		return ResponseEntity.ok(totalVotes);
	}

}
