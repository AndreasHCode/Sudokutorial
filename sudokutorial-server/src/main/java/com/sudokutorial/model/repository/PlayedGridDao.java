package com.sudokutorial.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sudokutorial.model.entity.PlayedGrid;
import com.sudokutorial.model.entity.Player;

@Repository
public interface PlayedGridDao extends JpaRepository<PlayedGrid, Long> {
	List<PlayedGrid> findAllByPlayer(Player player);
}
