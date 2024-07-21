package org.ere.contest.orderstarter.repository;

import org.ere.contest.orderstarter.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
}
