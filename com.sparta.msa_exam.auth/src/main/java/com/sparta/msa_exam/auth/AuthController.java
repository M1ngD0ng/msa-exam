package com.sparta.msa_exam.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.auth.dto.SignInRequest;
import com.sparta.msa_exam.auth.dto.SignupRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody SignupRequest request){
		return ResponseEntity.ok(authService.signUp(request));
	}

	@PostMapping("/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody SignInRequest request){
		return ResponseEntity.of(authService.signIn(request));
	}
}