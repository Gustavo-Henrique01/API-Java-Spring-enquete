package enquetes.sistemaenquetes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity // Habilita a segurança web do Spring
@EnableMethodSecurity // Permite usar anotações de pré-autorização como @PreAuthorize nos métodos de serviço/controller
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // Injeção de dependências via construtor
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Define a cadeia de filtros de segurança.
     * É aqui que as regras de segurança e os filtros são configurados.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (Cross-Site Request Forgery)
                // Para APIs RESTful com JWT, o CSRF geralmente é desabilitado porque não usamos sessões baseadas em cookies
                .csrf(AbstractHttpConfigurer::disable)
                // Configura o CORS (Cross-Origin Resource Sharing)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Configura as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público (sem autenticação) a estes endpoints:
                        .requestMatchers(
                                "/api/v1/auth/**", // Registro e Login
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"
                        ).permitAll() // Qualquer um pode acessar
                        // Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )
                // Configura o gerenciamento de sessão para ser STATELESS (sem estado)
                // Essencial para JWT, onde cada requisição contém o token para autenticação
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define o provedor de autenticação customizado (que usa seu UserDetailsService e PasswordEncoder)
                .authenticationProvider(authenticationProvider)
                // Adiciona o filtro JWT customizado ANTES do filtro padrão de autenticação por usuário/senha
                // Isso garante que o JWT seja validado primeiro
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Constrói e retorna a cadeia de filtros de segurança
    }

    /**
     * Configuração CORS para permitir requisições de outras origens.
     * Essencial para que seu frontend (se estiver em um domínio diferente) possa se comunicar com o backend.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permita as origens do seu frontend. Ex: "http://localhost:3000" para React/Angular
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://seu-frontend-em-producao.com"));
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        // Headers permitidos (geralmente "*")
        configuration.setAllowedHeaders(List.of("*"));
        // Permite o envio de credenciais (como cabeçalhos de autorização)
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todos os caminhos
        return source;
    }
}