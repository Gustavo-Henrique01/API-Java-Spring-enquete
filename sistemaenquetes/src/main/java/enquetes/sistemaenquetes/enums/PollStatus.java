package enquetes.sistemaenquetes.enums;

public enum PollStatus {
    ACTIVE,  // Enquete está aberta para votação
    CLOSED,  // Enquete foi encerrada manualmente ou pela data final
    INACTIVE; // Enquete criada, mas ainda não ativa (ou suspensa)
}