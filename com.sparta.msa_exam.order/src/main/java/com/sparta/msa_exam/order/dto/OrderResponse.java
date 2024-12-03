package com.sparta.msa_exam.order.dto;

import java.util.List;

import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderProduct;

import lombok.Getter;

@Getter
public class OrderResponse {
	private Long id;
	private List<Long> productIds;

	private OrderResponse(Long id, List<Long> productIds) {
		this.id = id;
		this.productIds = productIds;
	}

	public static OrderResponse fromEntity(Order order) {
		return new OrderResponse(
			order.getId(),
			order.getOrderProducts().stream().map(OrderProduct::getProductId).toList()
		);
	}
}
