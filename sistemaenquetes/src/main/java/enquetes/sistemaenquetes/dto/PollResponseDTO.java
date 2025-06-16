package enquetes.sistemaenquetes.dto;

import java.time.LocalDateTime;
import java.util.List;
import enquetes.sistemaenquetes.enums.PollStatus;
//Para retornar dados de uma enquete (incluindo opções e, opcionalmente, resultados)
public class PollResponseDTO {
    private Long id;
    private String question;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private PollStatus status;
    private String createdByUsername; 
    private List<OptionResponseDTO> options; 

    public PollResponseDTO() {}

    public PollResponseDTO(Long id, String question, String description, LocalDateTime startDate, LocalDateTime endDate, PollStatus status, String createdByUsername, List<OptionResponseDTO> options) {
        this.id = id;
        this.question = question;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdByUsername = createdByUsername;
        this.options = options;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public PollStatus getStatus() { return status; }
    public void setStatus(PollStatus status) { this.status = status; }
    public String getCreatedByUsername() { return createdByUsername; }
    public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; }
    public List<OptionResponseDTO> getOptions() { return options; }
    public void setOptions(List<OptionResponseDTO> options) { this.options = options; }
}