package com.sparta.msa_exam.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.OrderRepository;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderTransactionService {

	private final OrderRepository orderRepository;

	@Transactional
	public OrderResponse saveOrder(Order order) {
		return OrderResponse.fromEntity(orderRepository.save(order));
	}

	@Transactional
	public OrderResponse updateOrder(Order order, Long productId) {
		OrderProduct orderProduct = OrderProduct.of(order, productId);
		order.addOrderProduct(orderProduct);
		return OrderResponse.fromEntity(orderRepository.save(orderRepository.save(order)));
	}

	@Transactional(readOnly = true)
	public Order getValidateOrder(Long orderId, String username) {
		Order order = orderRepository.findByIdWithOrderProducts(orderId)
			.orElseThrow(()-> new IllegalArgumentException("존재하지 않는 주문 정보입니다."));
		if(!order.getCreatedBy().equals(username)) {
			throw new IllegalArgumentException("주문 접근 권한이 없습니다.");
		}
		return order;
	}

}

