package com.sudokutorial.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sudokutorial.model.entity.BaseGrid;

@Repository
public interface BaseGridDao extends JpaRepository<BaseGrid, Long> {
	public BaseGrid findByStringGrid(String stringGrid);
}
