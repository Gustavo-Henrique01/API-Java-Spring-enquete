package enquetes.sistemaenquetes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import enquetes.sistemaenquetes.dto.PollRequestDTO;
import enquetes.sistemaenquetes.dto.PollResponseDTO;
import enquetes.sistemaenquetes.dto.PollUpdateRequestDTO;
import enquetes.sistemaenquetes.enums.PollStatus;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.service.PollService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

	
	private final  PollService pollService ;

	public PollController(PollService pollService) {
	
		this.pollService = pollService;
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')")
	public ResponseEntity<PollResponseDTO> createPoll (@RequestBody @Valid PollRequestDTO pollRequestDTO){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Obter o ID do usuário autenticado (criador da enquete)

		User authenticatedUser = (User) authentication.getPrincipal();
        Long creatorUserId = authenticatedUser.getId();
        PollResponseDTO createdPoll = pollService.createPoll(pollRequestDTO, creatorUserId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPoll);
	}
	 @GetMapping 
	    @PreAuthorize("isAuthenticated()") 
	    public ResponseEntity<List<PollResponseDTO>> getAllPolls( 
	            @RequestParam(required = false) PollStatus status) { 
	        
	        List<PollResponseDTO> polls = pollService.getAllPolls(status);
	        return ResponseEntity.ok(polls);
 }
	@GetMapping("/{id}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<PollResponseDTO> getPollyId( @PathVariable Long id){
	PollResponseDTO pollResponseDTO = pollService.getPollById(id);
	return ResponseEntity.ok(pollResponseDTO);
	}
	
	
	@PutMapping("{id}/status")
	@PreAuthorize("hasAnyRole('CREATOR','ADMIN')")
	public ResponseEntity<PollResponseDTO> updatePollStatus (@PathVariable Long id, @RequestParam PollStatus pollStatus  ){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = (User) authentication.getPrincipal();
        Long creatorUserId = authenticatedUser.getId();	
        PollResponseDTO updatedPoll = pollService.updatePollStatus(id, pollStatus, creatorUserId);
		return ResponseEntity.ok(updatedPoll) ;
	}
	@DeleteMapping("{id}/delete")
	 @PreAuthorize("hasAnyRole('CREATOR', 'ADMIN')") 
	public ResponseEntity<Void> deletPoll (@PathVariable Long id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = (User) authentication.getPrincipal();
        Long creatorUserId = authenticatedUser.getId();	
        pollService.deletePoll(id, creatorUserId);
        return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("{id}/atulizar-enquete")
	@PreAuthorize("hasAnyRole('CREATOR','ADMIN')")
	public ResponseEntity<PollResponseDTO> updatePoll (@PathVariable Long id,  @RequestBody @Valid PollUpdateRequestDTO polls  ){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User authenticatedUser = (User) authentication.getPrincipal();
        Long creatorUserId = authenticatedUser.getId();	
        PollResponseDTO updatedPoll = pollService.updatePoll(id, polls, creatorUserId);
		return ResponseEntity.ok(updatedPoll) ;
	}
	
}
