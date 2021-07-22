package com.example.demo.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.demo.services.MyUserDetailsImplementation;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${livraison.app.jwtSecret}")
	private String jwtSecret;

	@Value("${livraison.app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		MyUserDetailsImplementation userPrincipal = (MyUserDetailsImplementation) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Signature du jwt n'est pas valide", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("jwt invalide", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT expirer , essayer de se reconnecter", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("le jwt n'est pas supporter", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("jwt string vide", e.getMessage());
		}

		return false;
	}
}
