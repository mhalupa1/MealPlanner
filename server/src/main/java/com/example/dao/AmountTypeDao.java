package com.example.dao;

import com.example.model.AmountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmountTypeDao extends JpaRepository<AmountType, Integer> {
}
