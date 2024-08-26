package com.cmall.cartservice.dao;

import com.cmall.cartservice.entity.Cart;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends CassandraRepository<Cart, UUID> {
    Optional<Cart> findByUserId(Integer userId);
}