package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import med.voll.api.domain.user.User;

@Service
public class TokenService {

	@Value("${api.security.secret}")
	private String apiSecret;
	
	public String generateToken(User user) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret);
		    return JWT.create()
		        .withIssuer("voll med")
		        .withSubject(user.getUsername())
//		        .withClaim("id", user.hashCode())
		        .withExpiresAt(expirationDay())
		        .sign(algorithm);

		} catch (JWTCreationException exception){
			System.out.println(exception.toString());
			throw new RuntimeException(exception);
		}
	}
	
	public String getSubject(String token) {
		if(token == null) {
			throw new RuntimeException();
		}
		DecodedJWT verifier = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret); // validando firma
		    verifier = JWT.require(algorithm)
		            .withIssuer("voll med")
		            .build()
		            .verify(token);
		    verifier.getSubject();
		} catch (JWTVerificationException exception) {
		    System.out.println(exception.toString());
		}
		if (verifier == null || verifier.getSubject() == null) {
		    throw new RuntimeException("Verifier invalid");
		}
		return verifier.getSubject();	
	}
	
	
	private Instant expirationDay() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
				}
}