package com.sparta.msa_exam.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.auth.service.AuthService;
import com.sparta.msa_exam.auth.dto.AuthRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/auth/sign-up")
	public ResponseEntity<?> signUp(@RequestBody AuthRequest request){
		return ResponseEntity.ok(authService.signUp(request));
	}

	@PostMapping("/auth/sign-in")
	public ResponseEntity<?> signIn(@RequestBody AuthRequest request){
		return ResponseEntity.ok(authService.signIn(request));
	}
}