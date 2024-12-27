package com.prosports.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prosports.models.MultiPack;

@Repository
public interface MultiPackRepository extends JpaRepository<MultiPack, Long> {

}
