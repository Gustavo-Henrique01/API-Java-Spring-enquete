package enquetes.sistemaenquetes.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service 
public class JwtService {

    // A chave secreta será injetada do arquivo application.properties
    // É crucial que esta chave seja forte e mantida em segurança.
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    // O tempo de expiração do token (em milissegundos) será injetado do application.properties
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    /**
     * Extrai o nome de usuário (subject) do token JWT.
     * @param token O token JWT.
     * @return O nome de usuário.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai um 'claim' específico do token JWT.
     * @param token O token JWT.
     * @param claimsResolver Uma função para resolver o 'claim' desejado.
     * @param <T> O tipo do 'claim' a ser extraído.
     * @return O valor do 'claim'.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Gera um token JWT para um determinado nome de usuário.
     * @param username O nome de usuário para o qual o token será gerado.
     * @return O token JWT gerado.
     */
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    /**
     * Gera um token JWT com 'claims' extras e um nome de usuário.
     * @param extraClaims Claims adicionais a serem incluídos no token.
     * @param username O nome de usuário.
     * @return O token JWT gerado.
     */
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return buildToken(extraClaims, username, jwtExpiration);
    }

    /**
     * Constrói o token JWT com base nos 'claims', nome de usuário e tempo de expiração.
     * @param extraClaims Claims adicionais.
     * @param username Nome de usuário.
     * @param expiration Tempo de expiração em milissegundos.
     * @return O token JWT completo.
     */
    private String buildToken(Map<String, Object> extraClaims, String username, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Define os claims (incluindo os extras)
                .setSubject(username)   // Define o assunto (geralmente o nome de usuário)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Data de expiração
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Assina o token com a chave secreta e algoritmo HS256
                .compact(); // Constrói e serializa o token
    }

    /**
     * Valida se um token JWT é válido para um determinado UserDetails.
     * Verifica se o nome de usuário no token corresponde ao UserDetails e se o token não expirou.
     * @param token O token JWT a ser validado.
     * @param userDetails Os detalhes do usuário para comparação.
     * @return True se o token é válido, false caso contrário.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Verifica se um token JWT expirou.
     * @param token O token JWT.
     * @return True se o token expirou, false caso contrário.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrai a data de expiração de um token JWT.
     * @param token O token JWT.
     * @return A data de expiração.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai todos os 'claims' de um token JWT.
     * @param token O token JWT.
     * @return Um objeto Claims contendo todos os dados do token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()) // Define a chave de assinatura
                .build()
                .parseClaimsJws(token) // Faz o parse do token
                .getBody(); // Obtém os claims
    }

    /**
     * Decodifica a chave secreta base64 e a retorna como um objeto Key.
     * @return A chave de assinatura.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}