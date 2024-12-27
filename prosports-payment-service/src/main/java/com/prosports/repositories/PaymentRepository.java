package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	// New method to check if a payment reference already exists
	boolean existsByPaymentReference(String paymentReference); // Added method to check for reference existence

	// New method to check if a key already exists
	boolean existsByKey(String key); // Check if a key already exists
	
	// New method to check if this orderKey already exists in the database
	Payment findByKey(String orderKey); //

}
