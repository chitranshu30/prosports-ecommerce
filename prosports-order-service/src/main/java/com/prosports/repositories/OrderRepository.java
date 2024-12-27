package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

	Orders findByOrderKey(String orderKey);
}
