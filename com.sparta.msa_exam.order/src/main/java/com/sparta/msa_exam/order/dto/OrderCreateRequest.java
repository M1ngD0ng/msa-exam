package com.sparta.msa_exam.order.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCreateRequest {
	private List<Long> productIds;
}
