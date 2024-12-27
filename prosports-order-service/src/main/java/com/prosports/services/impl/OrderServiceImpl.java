package com.prosports.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosports.dto.OrderRequest;
import com.prosports.dto.OrderResponse;
import com.prosports.dto.PaymentResponse;
import com.prosports.exceptions.CartNotFoundException;
import com.prosports.exceptions.OrderNotFoundException;
import com.prosports.model.Cart;
import com.prosports.model.CartState;
import com.prosports.model.Orders;
import com.prosports.model.PaymentState;
import com.prosports.openFeign.PaymentServiceopenFeign;
import com.prosports.repositories.CartRepository;
import com.prosports.repositories.OrderRepository;
import com.prosports.services.LineItemService;
import com.prosports.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private LineItemService lineItemService;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PaymentServiceopenFeign paymentServiceOpenFeign;

	/**
	 * Creates an order from an active cart. Throws CartNotFoundException if the
	 * cart is not found.
	 *
	 * @param orderRequest the data transfer object containing the details of the
	 *                     order to be created
	 * @return an OrderResponse containing the details of the created order
	 */
	@Override
	public OrderResponse createOrderFromCart(OrderRequest orderRequest) {
		Cart cart = cartRepository.findCartByCartKey(orderRequest.getCart().getCartKey());
		if (cart != null) {
			lineItemService.validateCartState(cart.getCartKey());
			Orders order = new Orders();
			order.setCart(cart);
			order.setOrderKey(cart.getCartKey());
			order.setPaymentState(PaymentState.PENDING);
			orderRepository.save(order);
			return modelMapper.map(order, OrderResponse.class);
		} else
			throw new CartNotFoundException("Cart not found with key " + orderRequest.getCart().getCartKey());
	}

	/**
	 * Updates the payment state of an order. Throws OrderNotFoundException if the
	 * order is not found. Throws CartNotFoundException if the cart is not found.
	 *
	 * @param orderKey the key of the order to update
	 * @return an OrderResponse containing the updated order details
	 */
	@Override
	public OrderResponse getPaymentStateFromPaymentService(String orderKey) {
		Orders order = orderRepository.findByOrderKey(orderKey);
		Cart cart = cartRepository.findCartByCartKey(orderKey);
		if (order != null) {
			if (cart != null) {
				lineItemService.validateCartState(cart.getCartKey());
				PaymentResponse paymentResponse = paymentServiceOpenFeign.getPaymentStatus(orderKey);
				PaymentState paymentState = paymentResponse.getPaymentStatus();
				if (paymentState == PaymentState.SUCCESS) {
					order.setPaymentState(PaymentState.SUCCESS);
					order.getCart().setCartState(CartState.ORDERED);
				} else {
					order.setPaymentState(PaymentState.FAILED);
					order.getCart().setCartState(CartState.CANCELLED);
				}
				cartRepository.save(cart);
				orderRepository.save(order);
				return modelMapper.map(order, OrderResponse.class);
			} else
				throw new CartNotFoundException("Cart not found with key " + orderKey);
		} else
			throw new OrderNotFoundException("Order not found with key " + orderKey);
	}
}
