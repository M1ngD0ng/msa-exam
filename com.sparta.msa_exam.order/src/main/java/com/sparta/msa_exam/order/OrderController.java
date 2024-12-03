package com.sparta.msa_exam.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.msa_exam.order.dto.OrderCreateRequest;
import com.sparta.msa_exam.order.global.CustomResponse;

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
}
