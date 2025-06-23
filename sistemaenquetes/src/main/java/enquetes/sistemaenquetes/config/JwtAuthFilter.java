package enquetes.sistemaenquetes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter; // Garante que o filtro seja executado apenas uma vez por requisição

import java.io.IOException;

@Component // Marca a classe como um componente do Spring
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Componente para carregar os detalhes do usuário

    // Injeção de dependências via construtor
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,       // Requisição HTTP
            @NonNull HttpServletResponse response,      // Resposta HTTP
            @NonNull FilterChain filterChain            // Cadeia de filtros do Spring Security
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); // Pega o cabeçalho "Authorization"
        final String jwt;
        final String username;

        // 1. Verifica se o cabeçalho Authorization está ausente ou não começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Prossegue para o próximo filtro sem autenticação JWT
            return;
        }

        // 2. Extrai o token JWT (removendo o prefixo "Bearer ")
        jwt = authHeader.substring(7);
        // 3. Extrai o nome de usuário do token
        username = jwtService.extractUsername(jwt);

        // 4. Se o nome de usuário foi extraído do token e o usuário ainda não está autenticado no contexto de segurança
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 5. Carrega os detalhes do usuário usando o UserDetailsService (que usa seu UserRepository)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 6. Valida o token contra os detalhes do usuário carregados
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 7. Se o token é válido, cria um objeto de autenticação
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,    // Principal: os detalhes do usuário
                        null,           // Credenciais: nulas, pois já autenticamos via token
                        userDetails.getAuthorities() // Autoridades/Roles do usuário
                );
                // Define detalhes adicionais da requisição para o token de autenticação
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 8. Define o objeto de autenticação no SecurityContextHolder
                // Isso informa ao Spring Security que o usuário está autenticado para esta requisição
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 9. Continua a cadeia de filtros do Spring Security
        filterChain.doFilter(request, response);
    }
}