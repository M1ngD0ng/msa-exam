package com.sparta.msa_exam.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sparta.msa_exam.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	@Query("SELECT o FROM Order o JOIN FETCH o.orderProducts WHERE o.id = :orderId")
	Optional<Order> findByIdWithOrderProducts(@Param("orderId") Long orderId);

}
