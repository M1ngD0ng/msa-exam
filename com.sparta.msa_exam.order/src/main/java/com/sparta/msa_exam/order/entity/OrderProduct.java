package com.sparta.msa_exam.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_order_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@Column(nullable = false)
	private Long productId;

	private OrderProduct(Order order, Long productId) {
		this.order = order;
		this.productId = productId;
	}

	public static OrderProduct of(Order order, Long productId) {
		return new OrderProduct(order, productId);
	}

}
