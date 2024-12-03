package com.sparta.msa_exam.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.product.dto.ProductCreateRequest;
import com.sparta.msa_exam.product.dto.ProductResponse;
import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional
	public ProductResponse createProduct(ProductCreateRequest productCreateRequest, String username) {
		Product product = productRepository.save(
			Product.of(
				productCreateRequest.getName(),
				productCreateRequest.getSupplyPrice(),
				username
			)
		);
		return new ProductResponse(
			product.getId(),
			product.getName(),
			product.getSupplyPrice(),
			product.getCreatedBy(),
			product.getCreatedAt().toString()
		);
	}
}
