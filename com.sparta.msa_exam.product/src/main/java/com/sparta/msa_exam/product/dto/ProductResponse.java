package com.sparta.msa_exam.product.dto;

import java.io.Serializable;

import com.sparta.msa_exam.product.entity.Product;

import lombok.Getter;

@Getter
public class ProductResponse implements Serializable {
	private Long id;
	private String name;
	private Integer supplyPrice;
	private String createdBy;
	private String createdAt;

	private ProductResponse(Long id, String name, Integer supplyPrice, String createdBy, String createdAt) {
		this.id = id;
		this.name = name;
		this.supplyPrice = supplyPrice;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}

	public static ProductResponse fromEntity(Product product) {
		return new ProductResponse(
			product.getId(),
			product.getName(),
			product.getSupplyPrice(),
			product.getCreatedBy(),
			product.getCreatedAt().toString()
		);
	}

}
