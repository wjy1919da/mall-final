package com.cmall.orderservice.dao;

import com.cmall.orderservice.entity.Order;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCassandraRepository<Order, UUID> {

}