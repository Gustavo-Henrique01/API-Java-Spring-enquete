package enquetes.sistemaenquetes.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
// Para criar ou atualizar uma enquete.
public class PollRequestDTO {

    @NotBlank(message = "A pergunta da enquete é obrigatória.")
    @Size(min = 5, max = 255, message = "A pergunta deve ter entre 5 e 255 caracteres.")
    private String question;

    private String description; // Opcional

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início não pode ser no passado.")
    private LocalDateTime startDate;

    @NotNull(message = "A data de término é obrigatória.")
    @FutureOrPresent(message = "A data de término não pode ser no inicio.")
    private LocalDateTime endDate;

    @NotNull(message = "As opções de voto são obrigatórias.")
    @Size(min = 2, message = "Uma enquete deve ter pelo menos 2 opções de voto.")
    private List<String> options; // Lista de textos das opções

    public PollRequestDTO() {}

    public PollRequestDTO(String question, String description, LocalDateTime startDate, LocalDateTime endDate, List<String> options) {
        this.question = question;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.options = options;
    }

 
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }
}