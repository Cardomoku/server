package com.jjweidon.cardomoku.repository;

import com.jjweidon.cardomoku.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
