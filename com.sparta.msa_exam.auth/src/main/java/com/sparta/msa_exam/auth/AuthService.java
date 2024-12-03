package com.sparta.msa_exam.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.auth.dto.AuthRequest;
import com.sparta.msa_exam.auth.dto.SignInResponse;
import com.sparta.msa_exam.auth.dto.SignupResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Value("${spring.application.name}")
	private String issuer;

	@Value("${service.jwt.access-expiration}")
	private Long accessExpiration;

	private final SecretKey secretKey;

	/**
	 * AuthService 생성자.
	 * Base64 URL 인코딩된 비밀 키를 디코딩하여 HMAC-SHA 알고리즘에 적합한 SecretKey 객체를 생성합니다.
	 *
	 * @param secretKey Base64 URL 인코딩된 비밀 키
	 */
	public AuthService(@Value("${service.jwt.secret-key}") String secretKey, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public SignupResponse signUp(AuthRequest authRequest) {
		if(userRepository.existsByUsername(authRequest.getUsername())) throw new IllegalArgumentException("이미 존재하는 아이디입니다.");

		User user = userRepository.save(
			User.of(
				authRequest.getUsername(),
				passwordEncoder.encode(authRequest.getPassword())
			)
		);
		return new SignupResponse(
			user.getId(),
			user.getUsername()
		);
	}

	public SignInResponse signIn(AuthRequest authRequest) {
		User user = userRepository.findByUsername(authRequest.getUsername()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");

		return new SignInResponse(
			createAccessToken(user.getUsername())
		);
	}

	/**
	 * 사용자 ID를 받아 JWT 액세스 토큰을 생성합니다.
	 *
	 * @param username 사용자 ID
	 * @return 생성된 JWT 액세스 토큰
	 */
	public String createAccessToken(String username) {
		return Jwts.builder()
			// 사용자 ID를 클레임으로 설정
			.claim("username", username)
			// JWT 발행자를 설정
			.issuer(issuer)
			// JWT 발행 시간을 현재 시간으로 설정
			.issuedAt(new Date(System.currentTimeMillis()))
			// JWT 만료 시간을 설정
			.expiration(new Date(System.currentTimeMillis() + accessExpiration))
			// SecretKey를 사용하여 HMAC-SHA512 알고리즘으로 서명
			.signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
			// JWT 문자열로 컴팩트하게 변환
			.compact();
	}
}