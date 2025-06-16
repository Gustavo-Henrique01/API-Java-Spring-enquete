package enquetes.sistemaenquetes.dto;

import java.time.LocalDateTime;
//Para retornar a confirmação do voto
public class VoteResponseDTO {
    private Long voteId;
    private String username;
    private String optionText;
    private LocalDateTime votedAt;

    public VoteResponseDTO() {}

    public VoteResponseDTO(Long voteId, String username, String optionText, LocalDateTime votedAt) {
        this.voteId = voteId;
        this.username = username;
        this.optionText = optionText;
        this.votedAt = votedAt;
    }

    public Long getVoteId() { return voteId; }
    public void setVoteId(Long voteId) { this.voteId = voteId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }
    public LocalDateTime getVotedAt() { return votedAt; }
    public void setVotedAt(LocalDateTime votedAt) { this.votedAt = votedAt; }
}