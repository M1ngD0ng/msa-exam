package com.sparta.msa_exam.product.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@CacheEvict(cacheNames = "productAllCache", allEntries = true)
	public ProductResponse createProduct(ProductCreateRequest productCreateRequest, String username) {
		Product product = productRepository.save(
			Product.of(
				productCreateRequest.getName(),
				productCreateRequest.getSupplyPrice(),
				username
			)
		);
		return ProductResponse.fromEntity(product);
	}

	@Cacheable(cacheNames = "productAllCache", key = "methodName")
	public List<ProductResponse> getProducts(){
		return productRepository.findAllOrderByCreatedAtDesc().stream().map(ProductResponse::fromEntity).toList();
	}

	public void validateProduct(Long productId) {
		if(!productRepository.existsById(productId)) throw new IllegalArgumentException("존재하지 않는 상품입니다.");
	}
}
