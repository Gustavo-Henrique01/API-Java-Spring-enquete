package enquetes.sistemaenquetes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enquetes.sistemaenquetes.model.Poll;
import enquetes.sistemaenquetes.model.User;
import enquetes.sistemaenquetes.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	  // Verificar se um usuário já votou em uma enquete (crucial!)
    Optional<Vote> findByUserAndPoll(User user, Poll poll);

    // Contar votos para uma opção específica (para exibir resultados)
    long countByOptionId(Long optionId);

    // Contar votos para uma enquete (somando votos de todas as opções)
    long countByPollId(Long pollId);
    
   // listar votos de um usuário.
    List<Vote> findByUser(User user);
}
