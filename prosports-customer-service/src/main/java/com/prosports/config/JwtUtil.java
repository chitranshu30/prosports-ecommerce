package com.prosports.config;

import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.prosports.models.Role;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Configuration
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKeyValue;

	private SecretKey secretKey;

	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyValue)); // Ensure BASE64URL decoding
	}

	public String generateToken(String username, Role roles) {
		return Jwts.builder().setSubject(username)
				.claim("roles", roles.name()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1-hour expiry
				.signWith(secretKey, SignatureAlgorithm.HS256) // Ensure HS256 is used
				.compact();
	}

	public void validateToken(final String token) {
		Jwts.parserBuilder().setSigningKey(getSignKey()) // Ensure the key is correctly loaded
				.build().parseClaimsJws(token); // This checks the signature
	}

	public Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(secretKeyValue); // Use BASE64URL decoder
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
