package com.sparta.msa_exam.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponse {
	private Long userId;
	private String username;
}
