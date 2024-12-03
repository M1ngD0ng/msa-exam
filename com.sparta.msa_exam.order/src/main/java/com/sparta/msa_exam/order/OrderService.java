package com.sparta.msa_exam.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.msa_exam.order.dto.OrderCreateRequest;
import com.sparta.msa_exam.order.dto.OrderResponse;
import com.sparta.msa_exam.order.dto.OrderUpdateRequest;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;
import com.sparta.msa_exam.order.global.CustomResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final ProductClient productClient;
	private final OrderRepository orderRepository;

	@Transactional
	public OrderResponse createOrder(OrderCreateRequest request, String username) {
		validateProducts(request.getProductIds());

		Order order = Order.of(username);

		for (Long productId : request.getProductIds()) {
			OrderProduct orderProduct = OrderProduct.of(order, productId);
			order.addOrderProduct(orderProduct);
		}

		return OrderResponse.fromEntity(
			orderRepository.save(order)
		);
	}

	@Transactional
	public OrderResponse updateOrder(OrderUpdateRequest request, String username, Long orderId) {
		Order order = getValidatedOrder(orderId, username);
		validateProduct(request.getProductId());

		OrderProduct orderProduct = OrderProduct.of(order, request.getProductId());
		order.addOrderProduct(orderProduct);
		return OrderResponse.fromEntity(orderRepository.save(order));
	}

	private void validateProduct(Long productId) {
		CustomResponse<Void> response = productClient.validateProduct(productId);
		if (response.getStatus() != HttpStatus.OK.value())
			throw new IllegalArgumentException("주문할 수 없는 상품입니다.");
	}

	private void validateProducts(List<Long> productIds) {
		if (productIds.isEmpty())
			throw new IllegalArgumentException("주문할 상품을 1개 이상 입력해주세요.");
		for (Long productId : productIds) {
			validateProduct(productId);
		}
	}

	private Order getValidatedOrder(Long orderId, String username) {
		Order order = orderRepository.findById(orderId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문 정보입니다."));
		if (!order.getCreatedBy().equals(username))
			throw new IllegalArgumentException("주문 수정 권한이 없습니다.");
		return order;
	}
}
