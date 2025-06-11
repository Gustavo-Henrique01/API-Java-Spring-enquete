package enquetes.sistemaenquetes.enums;


public enum UserRole {
	
    ROLE_VOTER,    // Para usuários que só votam
    ROLE_CREATOR,  // Para usuários que criam enquetes e votam
    ROLE_ADMIN;    // Para administradores (gerenciam tudo)

    // Opcional: Adicionar um método para obter o nome com o prefixo "ROLE_"
    public String getRoleName() {
        return name();
    }
}