package com.sparta.msa_exam.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.product.dto.ProductCreateRequest;
import com.sparta.msa_exam.product.global.CustomResponse;
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
		return ResponseEntity.status(HttpStatus.OK)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.OK.value())
					.message("상품 등록이 완료되었습니다.")
					.data(productService.createProduct(productCreateRequest, username))
					.build()
			);
	}
}
