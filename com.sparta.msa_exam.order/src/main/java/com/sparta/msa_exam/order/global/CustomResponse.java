package com.sparta.msa_exam.order.global;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomResponse<T> {
	private int status;
	private String message;
	private T data;
}
