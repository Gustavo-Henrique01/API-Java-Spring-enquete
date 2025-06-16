package enquetes.sistemaenquetes.dto;

import jakarta.validation.constraints.NotNull;
//Para registrar um voto.
public class VoteRequestDTO {

    @NotNull(message = "O ID da enquete é obrigatório.")
    private Long pollId;

    @NotNull(message = "O ID da opção de voto é obrigatório.")
    private Long optionId;

    public VoteRequestDTO() {}

    public VoteRequestDTO(Long pollId, Long optionId) {
        this.pollId = pollId;
        this.optionId = optionId;
    }

 
    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }
    public Long getOptionId() { return optionId; }
    public void setOptionId(Long optionId) { this.optionId = optionId; }
}