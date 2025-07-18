# Sistema de Enquetes API 🗳️

![Java](https://img.shields.io/badge/Java-17-%23ED8B00?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-%236DB33F?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-%23316192?logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Auth-%23000000?logo=jsonwebtokens)

Uma API RESTful completa para gerenciamento de enquetes com autenticação segura e controle de acesso baseado em roles.

## 📌 Visão Geral

Este projeto é uma API de Sistema de Enquetes desenvolvida com Spring Boot que permite:

- Criação e gerenciamento de enquetes com múltiplas opções
- Sistema de votação seguro
- Controle de acesso granular com 3 níveis de permissão
- Autenticação via JWT (JSON Web Tokens)

## ✨ Funcionalidades Principais

### 🔐 Autenticação & Autorização
- Registro de usuários com roles: `VOTER`, `CREATOR`, `ADMIN`
- Login com geração de JWT
- Proteção de endpoints com `@PreAuthorize`

### 📊 Gerenciamento de Enquetes
- Criação de enquetes (inicialmente como rascunho)
- Transição de status: `DRAFT` → `ACTIVE` → `CLOSED`
- Listagem com filtros por status
- Atualização e exclusão (com restrições)

### 🗳️ Sistema de Votação
- Voto único por usuário por enquete
- Contagem de votos em tempo real
- Visualização de resultados

## 🛠️ Tecnologias Utilizadas

| Categoria       | Tecnologias                                                                 |
|-----------------|-----------------------------------------------------------------------------|
| Backend         | Java 17, Spring Boot (Web, Data JPA, Security), Hibernate                   |
| Banco de Dados  | PostgreSQL                                                                  |
| Segurança       | Spring Security, JWT (JJWT)                                                 |
| Documentação    | Swagger UI (OpenAPI 3.0)                                                    |
| Validação       | Jakarta Bean Validation                                                    |
| Build           | Maven                                                                       |

## 🚀 Como Executar

### Pré-requisitos
- JDK 17+
- Maven 3.8+
- PostgreSQL 10+
- Cliente API (Postman, Insomnia, etc.)

### Configuração Inicial
1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/sistema-enquetes-api.git
   ```

2. Configure o banco de dados:
   - Crie um banco `enquetes_db` no PostgreSQL
   - Atualize as credenciais no `application.properties`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/enquetes_db
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     ```

3. Gere uma chave secreta JWT (mínimo 256 bits):
   ```java
   import java.security.SecureRandom;
   import java.util.Base64;
   
   public class KeyGenerator {
       public static void main(String[] args) {
           SecureRandom secureRandom = new SecureRandom();
           byte[] key = new byte[32]; // 256 bits
           secureRandom.nextBytes(key);
           String encodedKey = Base64.getEncoder().encodeToString(key);
           System.out.println(encodedKey);
       }
   }
   ```
   Adicione a chave gerada em:
   ```properties
   application.security.jwt.secret-key=SUA_CHAVE_AQUI
   ```

### Executando a Aplicação
```bash
mvn clean install
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

Acesse a documentação interativa via Swagger UI:
- http://localhost:8080/swagger-ui.html

### Principais Endpoints
| Método | Endpoint                | Descrição                              |
|--------|-------------------------|----------------------------------------|
| POST   | /api/v1/auth/register   | Registrar novo usuário                 |
| POST   | /api/v1/auth/login      | Login e obtenção de JWT                |
| POST   | /api/v1/polls           | Criar nova enquete                     |
| GET    | /api/v1/polls           | Listar enquetes (com filtros)          |
| POST   | /api/v1/votes           | Registrar voto                         |

## 🤝 Contribuição
Contribuições são bem-vindas! Siga estes passos:
1. Faça um fork do projeto
2. Crie sua branch (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request


---
Desenvolvido com ❤️ por [Gustavo Henrique](https://github.com/Gustavo-Henrique01) | [LinkedIn](https://linkedin.com/in/seu-perfil](Https://www.linkedin.com/in/gustavo-henrique-78152b306?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app))
