package com.example.dao;

import com.example.model.MeasuringUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuringUnitDao extends JpaRepository<MeasuringUnit, Integer> {
}
