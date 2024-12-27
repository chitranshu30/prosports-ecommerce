package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findCartByCartKey(String cartKey);
}
