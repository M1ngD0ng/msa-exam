package com.sparta.msa_exam.gateway;

import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

	@Value("${service.jwt.secret-key}")
	private String secretKey;

	private static final Logger logger = Logger.getLogger(LocalJwtAuthenticationFilter.class.getName());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		if (path.startsWith("/auth/sign-")) {
			return chain.filter(exchange);  // /signIn 경로는 필터를 적용하지 않음
		}

		String token = extractToken(exchange);

		if (token == null || !validateToken(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

	private String extractToken(ServerWebExchange exchange) {
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	private boolean validateToken(String token) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
			Jws<Claims> claimsJws = Jwts.parser()
				.verifyWith(key)
				.build().parseSignedClaims(token);
			log.info("#####payload :: " + claimsJws.getPayload().toString());

			// 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String extractUserIdFromToken(String token) {
		try {
			// 토큰 파싱 및 클레임 추출
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));

			Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
			// "username" 클레임에서 사용자 ID 추출
			return claims.get("username", String.class);
		} catch (Exception e) {
			// 예외 발생 시 null 반환 (토큰이 유효하지 않음)
			return null;
		}
	}


}