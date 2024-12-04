package com.sparta.msa_exam.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.order.dto.OrderCreateRequest;
import com.sparta.msa_exam.order.dto.OrderUpdateRequest;
import com.sparta.msa_exam.order.dto.CustomResponse;
import com.sparta.msa_exam.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<?> createOrder(
		@RequestBody OrderCreateRequest orderCreateRequest,
		@RequestHeader("X-Username") String username
	){
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.CREATED.value())
					.message("상품 주문이 완료되었습니다.")
					.data(orderService.createOrder(orderCreateRequest, username))
					.build()
			);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<?> updateOrder(
		@RequestBody OrderUpdateRequest orderUpdateRequest,
		@RequestHeader("X-Username") String username,
		@PathVariable Long orderId
	){
		return ResponseEntity.status(HttpStatus.OK)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.OK.value())
					.message("상품 주문 수정이 완료되었습니다.")
					.data(orderService.updateOrder(orderUpdateRequest, username, orderId))
					.build()
			);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrder(
		@RequestHeader("X-Username") String username,
		@PathVariable Long orderId
	){
		return ResponseEntity.status(HttpStatus.OK)
			.body(
				CustomResponse.builder()
					.status(HttpStatus.OK.value())
					.message("상품 주문 단건 조회가 완료되었습니다.")
					.data(orderService.getOrder(username, orderId))
					.build()
			);
	}
}
