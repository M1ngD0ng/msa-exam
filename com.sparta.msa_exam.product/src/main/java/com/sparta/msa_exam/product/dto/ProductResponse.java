package com.sparta.msa_exam.product.dto;

import com.sparta.msa_exam.product.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ProductResponse {
	private Long id;
	private String name;
	private Integer supplyPrice;
	private String createdBy;
	private String createdAt;

	private ProductResponse(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.supplyPrice = product.getSupplyPrice();
		this.createdBy = product.getCreatedBy();
		this.createdAt = product.getCreatedAt().toString();
	}

	public static ProductResponse fromEntity(Product product) {
		return new ProductResponse(product);
	}

}
