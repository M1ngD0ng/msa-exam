package com.sparta.msa_exam.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
	@NotBlank(message = "상품명을 입력해주세요.")
	private String name;

	@NotNull(message = "상품 가격을 입력해주세요.")
	private Integer supplyPrice;
}
