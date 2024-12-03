package com.sparta.msa_exam.product.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer supplyPrice;

	@Column(nullable = false, updatable = false)
	private String createdBy;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	private Product(String name, Integer supplyPrice, String createdBy) {
		this.name = name;
		this.supplyPrice = supplyPrice;
		this.createdBy = createdBy;
		this.createdAt = LocalDateTime.now();
	}

	public static Product of(String name, Integer supplyPrice, String createdBy) {
		return new Product(name, supplyPrice, createdBy);
	}
}
