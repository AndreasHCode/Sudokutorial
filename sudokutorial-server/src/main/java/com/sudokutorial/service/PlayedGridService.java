package com.sudokutorial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudokutorial.model.entity.PlayedGrid;
import com.sudokutorial.model.entity.Player;
import com.sudokutorial.model.repository.PlayedGridDao;

@Service
public class PlayedGridService {
	
	@Autowired
	PlayedGridDao playedGridDao;
	
	public void savePlayedGrid(PlayedGrid playedGrid) {
		playedGridDao.save(playedGrid);
	}
	
	public PlayedGrid loadPlayedGrid(Long id) {
		return playedGridDao.getOne(id);
	}
	
	public List<PlayedGrid> getPlayersGrids(Player player) {
		return playedGridDao.findAllByPlayer(player);
	}

}
