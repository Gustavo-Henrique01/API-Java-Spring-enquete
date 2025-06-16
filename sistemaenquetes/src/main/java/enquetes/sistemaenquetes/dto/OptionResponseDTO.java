package enquetes.sistemaenquetes.dto;
//DTO para as opções de uma enquete (pode incluir a contagem de votos)
public class OptionResponseDTO {
    private Long id;
    private String text;
    private Long voteCount; 

    public OptionResponseDTO() {}

    public OptionResponseDTO(Long id, String text, Long voteCount) {
        this.id = id;
        this.text = text;
        this.voteCount = voteCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public Long getVoteCount() { return voteCount; }
    public void setVoteCount(Long voteCount) { this.voteCount = voteCount; }
}