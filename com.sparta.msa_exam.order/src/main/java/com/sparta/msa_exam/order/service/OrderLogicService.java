package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.OrderCreateRequest;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.dto.CustomResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLogicService {

	private final ProductClient productClient;

	@CircuitBreaker(name = "productService", fallbackMethod = "fallbackValidateProducts")
	public void validateProducts(List<Long> productIds) {
		if (productIds.isEmpty())
			throw new IllegalArgumentException("주문할 상품을 1개 이상 입력해주세요.");
		for (Long productId : productIds) {
			validateProduct(productId);
		}
	}

	private void validateProduct(Long productId) {
		CustomResponse<Void> response = productClient.validateProduct(productId);
		if (response.getStatus() != HttpStatus.OK.value()) {
			throw new IllegalArgumentException("주문할 수 없는 상품입니다.");
		}
	}

	public Order createOrderEntity(OrderCreateRequest request, String username) {
		Order order = Order.of(username);
		for (Long productId : request.getProductIds()) {
			OrderProduct orderProduct = OrderProduct.of(order, productId);
			order.addOrderProduct(orderProduct);
		}
		return order;
	}

	public void fallbackValidateProducts(List<Long> productIds, Throwable throwable) {
		System.err.println("Fallback executed due to: " + throwable.getMessage());
		throw new RuntimeException("잠시 후 다시 시도해주세요.");
	}
}
