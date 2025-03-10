package com.jjweidon.cardomoku.domain.repository;

import com.jjweidon.cardomoku.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
