 package com.prosports.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dto.PaymentResponse;
import com.prosports.entity.Payment;
import com.prosports.entity.PaymentMethodInfo;
import com.prosports.entity.PaymentMethodType;
import com.prosports.entity.PaymentStatus;
import com.prosports.exception.ResourceNotFoundException;
import com.prosports.exception.UniqueKeyViolationException;
import com.prosports.repositories.PaymentRepository;
import com.prosports.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SecureRandom random;

	/**
	 * Generates a unique payment reference by appending a random 4-digit number to
	 * the prefix "prosports". Ensures the generated reference is unique by checking
	 * against the database.
	 *
	 * @return a unique payment reference string
	 */
	private String generatePaymentReference() {
		log.debug("Generating a new payment reference");
		String reference = "prosports" + generateRandomNumber();
		while (paymentRepository.existsByPaymentReference(reference)) {
			log.warn("Duplicate payment reference detected: {}. Regenerating.", reference);
			reference = "prosports" + generateRandomNumber(); // Regenerate if not unique
		}
		log.info("Generated payment reference: {}", reference);
		return reference;
	}

	/**
	 * Generates a random 4-digit number as a string.
	 *
	 * @return a random 4-digit number as a string
	 */
	private String generateRandomNumber() {
		return String.format("%04d", random.nextInt(10000));
	}

	/**
	 * Processes a payment by creating a new Payment entity, setting its properties,
	 * and saving it to the database. If the key already exists or if the key or
	 * amount is null, appropriate exceptions are thrown.
	 *
	 * @param key    the unique key for the payment
	 * @param amount the amount for the payment
	 * @return a PaymentResponse object containing the payment details
	 */
	@Override
	public PaymentResponse processPayment(String key, BigDecimal amount) {
		log.info("Processing payment for key: {}", key);
		if (!paymentRepository.existsByKey(key)) {
			if (key != null && amount != null) {
				Payment payment = new Payment();
				payment.setKey(key);

				PaymentMethodInfo paymentMethodInfo = new PaymentMethodInfo();
				if (Math.random() < 0.3) {
					paymentMethodInfo.setType(PaymentMethodType.UPI);
				} else if (Math.random() < 0.6 && Math.random() >= 0.3) {
					paymentMethodInfo.setType(PaymentMethodType.PAYPAL);
				} else {
					paymentMethodInfo.setType(PaymentMethodType.CREDIT_CARD);
				}
				payment.setPaymentMethodInfo(paymentMethodInfo);

				String paymentReference = generatePaymentReference();
				payment.setPaymentReference(paymentReference);

				payment.setPaymentDate(LocalDateTime.now());
				if (Math.random() > 0.5) {
					payment.setPaymentStatus(PaymentStatus.SUCCESS);
					log.info("Payment succeeded for key: {}", key);
				} else {
					payment.setPaymentStatus(PaymentStatus.FAILED);
					log.warn("Payment failed for key: {}", key);
				}
				payment.setAmount(amount);

				paymentRepository.save(payment);
				log.info("Payment processed and saved with reference: {}", paymentReference);

				return modelMapper.map(payment, PaymentResponse.class);
			} else {
				log.error("Null key or amount provided for payment processing");
				throw new ResourceNotFoundException("Provided order key or the order value is null");
			}
		} else {
			log.error("Duplicate key detected: {}", key);
			throw new UniqueKeyViolationException("The key " + key + " already exists. Please provide a unique key.");
		}
	}

	/**
	 * Retrieves the payment status for a given order key. If the key exists,
	 * returns the payment details. If the key does not exist, throws a
	 * ResourceNotFoundException.
	 *
	 * @param orderKey the unique key for the payment
	 * @return a PaymentResponse object containing the payment details
	 */
	@Override
	public PaymentResponse getPaymentStatus(String orderKey) {
		log.info("Retrieving payment status for key: {}", orderKey);
		if (paymentRepository.existsByKey(orderKey)) {
			Payment payment = paymentRepository.findByKey(orderKey);
			log.info("Payment status retrieved for key: {}", orderKey);
			return modelMapper.map(payment, PaymentResponse.class);
		} else {
			log.error("Payment key not found: {}", orderKey);
			throw new ResourceNotFoundException("The key '" + orderKey + "' does not exist");
		}
	}

}
