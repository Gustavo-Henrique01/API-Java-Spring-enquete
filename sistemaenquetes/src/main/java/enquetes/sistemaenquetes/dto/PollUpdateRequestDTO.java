package enquetes.sistemaenquetes.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;

public class PollUpdateRequestDTO {

    private String question;
    private String description;
    @FutureOrPresent(message = "A data de início não pode ser no passado.")

    private LocalDateTime startDate;
    @FutureOrPresent(message = "A data de término não pode ser no inicio.")

    private LocalDateTime endDate;
 

    public PollUpdateRequestDTO() {
    }

    public PollUpdateRequestDTO(String question, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.question = question;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

   
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}