package com.sparta.msa_exam.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.msa_exam.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
