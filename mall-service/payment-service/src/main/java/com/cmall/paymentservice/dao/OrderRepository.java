package com.cmall.paymentservice.dao;

import com.cmall.paymentservice.entity.Order;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface OrderRepository extends CassandraRepository<Order, UUID> {

}
