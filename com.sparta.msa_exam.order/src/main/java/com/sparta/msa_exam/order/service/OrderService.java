package com.sparta.msa_exam.order.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sparta.msa_exam.order.dto.OrderCreateRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.dto.OrderUpdateRequest;
import com.sparta.msa_exam.order.entity.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderLogicService orderLogicService;
	private final OrderTransactionService orderTransactionService;

	public OrderResponse createOrder(OrderCreateRequest request, String username) {
		orderLogicService.validateProducts(request.getProductIds());
		Order order = orderLogicService.createOrderEntity(request, username);

		return orderTransactionService.saveOrder(order);
	}

	public OrderResponse updateOrder(OrderUpdateRequest request, String username, Long orderId) {
		Order order = orderTransactionService.getValidateOrder(orderId, username);

		orderLogicService.validateProducts(List.of(request.getProductId()));

		return orderTransactionService.updateOrder(order, request.getProductId());
	}

	@Cacheable(cacheNames = "orderCache", key = "args[1]")
	public OrderResponse getOrder(String username, Long orderId) {
		Order order = orderTransactionService.getValidateOrder(orderId, username);
		return OrderResponse.fromEntity(order);
	}
}
