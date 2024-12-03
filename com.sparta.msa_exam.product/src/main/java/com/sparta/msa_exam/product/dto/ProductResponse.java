package com.sparta.msa_exam.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
	private Long productId;
	private String name;
	private Integer supplyPrice;
	private String createdBy;
	private String createdAt;
}
