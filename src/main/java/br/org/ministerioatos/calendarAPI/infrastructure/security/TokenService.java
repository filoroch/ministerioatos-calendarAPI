package br.org.ministerioatos.calendarAPI.infrastructure.security;

import br.org.ministerioatos.calendarAPI.infrastructure.data.models.UserDataJpa;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

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

    /// Definir data de expiração do token
    private Instant defineExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
