package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.model.LineItem;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

}
