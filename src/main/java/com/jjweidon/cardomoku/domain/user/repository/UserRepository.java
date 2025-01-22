package com.jjweidon.cardomoku.domain.user.repository;

import com.jjweidon.cardomoku.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
