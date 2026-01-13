package br.org.ministerioatos.calendarAPI.infrastructure.security;

import br.org.ministerioatos.calendarAPI.infrastructure.data.models.UserDataJpa;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public TokenService(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public String generateToken(UserDataJpa user)
    {
        try {
            var algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("calendarAPI")             /// quem emitiu
                    .withSubject(user.getUsername())       /// para quem o token foi gerado
                    .withExpiresAt(defineExpirationDate()) /// definir data de expiração
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("erro ao gerar o token", e);
        }
    }

    public String verifyToken(String token)
    {
        try {
            var algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("calendarAPI")  /// Verifica se o emissor é o mesmo
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado", e);
        }
    }

    /// Definir data de expiração do token
    private Instant defineExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
