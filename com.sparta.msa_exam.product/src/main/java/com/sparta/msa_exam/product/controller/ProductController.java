package com.sparta.msa_exam.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.product.dto.ProductCreateRequest;
import com.sparta.msa_exam.product.dto.CustomResponse;
import com.sparta.msa_exam.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<?> createProduct(
		@RequestBody ProductCreateRequest productCreateRequest,
		@RequestHeader("X-Username") String username
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.CREATED.value())
					.message("상품 등록이 완료되었습니다.")
					.data(productService.createProduct(productCreateRequest, username))
					.build()
			);
	}

	@GetMapping
	public ResponseEntity<?> getProducts() {
		return ResponseEntity.status(HttpStatus.OK)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.OK.value())
					.message("상품 목록 조회가 완료되었습니다.")
					.data(productService.getProducts())
					.build()
			);
	}

	@GetMapping("/validate")
	public ResponseEntity<?> validateProduct(@RequestParam Long productId) {
		productService.validateProduct(productId);
		return ResponseEntity.status(HttpStatus.OK)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.OK.value())
					.message("주문 가능한 상품입니다.")
					.build()
			);
	}
}
