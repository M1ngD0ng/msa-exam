package com.sparta.msa_exam.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.msa_exam.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByUsername(String username);
	Optional<User> findByUsername(String username);
}
