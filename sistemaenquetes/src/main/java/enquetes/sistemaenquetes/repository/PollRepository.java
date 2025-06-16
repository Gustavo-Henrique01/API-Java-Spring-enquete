package enquetes.sistemaenquetes.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enquetes.sistemaenquetes.enums.PollStatus;
import enquetes.sistemaenquetes.model.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll,Long>{
    List<Poll> findByCreatedBy_Id(Long userId);
    // Encontrar enquetes ativas em um determinado período
    List<Poll> findByStatusAndStartDateBeforeAndEndDateAfter(
        PollStatus status, LocalDateTime now1, LocalDateTime now2);
    // Encontrar enquetes ativas ou fechadas por criador
    List<Poll> findByCreatedBy_IdAndStatusIn(Long userId, List<PollStatus> statuses);
}
