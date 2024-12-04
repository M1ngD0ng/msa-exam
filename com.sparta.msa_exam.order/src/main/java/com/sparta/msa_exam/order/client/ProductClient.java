package com.sparta.msa_exam.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sparta.msa_exam.order.dto.CustomResponse;

@FeignClient(name = "product-service")
public interface ProductClient {
	@GetMapping("/products/validate")
	CustomResponse<Void> validateProduct(@RequestParam Long productId);
}
