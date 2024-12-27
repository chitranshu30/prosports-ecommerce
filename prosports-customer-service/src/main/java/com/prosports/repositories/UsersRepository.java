package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.models.Customer;
import com.prosports.models.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	User findByUsername(String username);
}
