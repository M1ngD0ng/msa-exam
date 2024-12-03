package com.sparta.msa_exam.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.msa_exam.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
	List<Product> findAllOrderByCreatedAtDesc();
}
