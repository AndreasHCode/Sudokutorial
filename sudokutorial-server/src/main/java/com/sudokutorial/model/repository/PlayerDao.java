package com.sudokutorial.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sudokutorial.model.entity.Player;

@Repository
public interface PlayerDao extends JpaRepository<Player, Long> {
	Player findByEmail(String email);
}
