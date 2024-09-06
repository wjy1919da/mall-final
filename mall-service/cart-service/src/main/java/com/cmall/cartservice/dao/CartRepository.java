package com.cmall.cartservice.dao;

import com.cmall.cartservice.entity.Cart;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends CassandraRepository<Cart, UUID> {
    Optional<Cart> findByUserId(Integer userId);
}