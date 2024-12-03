package com.sparta.msa_exam.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomResponse<T> {
	private int status;
	private String message;
	private T data;
}
