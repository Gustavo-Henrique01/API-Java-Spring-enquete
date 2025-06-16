package enquetes.sistemaenquetes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enquetes.sistemaenquetes.model.Option;
import enquetes.sistemaenquetes.model.Poll;

@Repository
public interface OptionRepository  extends JpaRepository<Option, Long>{

	 // Encontrar todas as opções para uma enquete específica
    List<Option> findByPoll(Poll poll);
    
    // Ou pelo ID da enquete
    List<Option> findByPollId(Long pollId);

    // Encontrar uma opção específica por ID e garantir que ela pertença a uma enquete
    Optional<Option> findByIdAndPollId(Long optionId, Long pollId);

}
