package com.sudokutorial.controller.rest;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sudokutorial.generator.GridHelper;
import com.sudokutorial.generator.GridSolver;
import com.sudokutorial.generator.items.Difficulty;
import com.sudokutorial.generator.items.RuleType;
import com.sudokutorial.generator.items.SolutionStep;
import com.sudokutorial.generator.items.SudokuGrid;
import com.sudokutorial.model.entity.BaseGrid;
import com.sudokutorial.model.entity.PlayedGrid;
import com.sudokutorial.model.entity.Player;
import com.sudokutorial.service.BaseGridService;
import com.sudokutorial.service.PlayedGridService;
import com.sudokutorial.service.PlayerService;

@RestController
public class SudokuGridController {
	
	SudokuGrid sudokuGrid;
	
	@Autowired
	PlayedGridService playedGridService;
	
	@Autowired
	BaseGridService baseGridService;
	
	@Autowired
	PlayerService playerService;
	
	@GetMapping (value = "/grid")
	public SudokuGrid getGrid(@RequestParam("d") Integer difficutlyInt) {
		Difficulty difficulty = Difficulty.values()[difficutlyInt - 1];
		sudokuGrid = new SudokuGrid();
		GridHelper.generateGrid(sudokuGrid, difficulty);
		
		return sudokuGrid;
	}
	
	@PostMapping (value = "/grid")
	public void putGrid(@RequestBody SudokuGrid sudokuGrid) {
			
	}

	@PostMapping (value = "/makeEntries")
	public SudokuGrid postMakeEntries(@RequestBody SudokuGrid sudokuGridBack) {
		SudokuGrid cleanedGrid = sudokuGridBack.cloneSudokuGrid();
		GridSolver.makeUniqueEntries(cleanedGrid);
		
		return cleanedGrid;
	}

	@PostMapping (value = "/applyRule/{ruleType}")
	public SudokuGrid postApplyRule(@RequestBody SudokuGrid sudokuGridBack, @PathVariable("ruleType") String ruleType) {
		SudokuGrid cleanedGrid = sudokuGridBack.cloneSudokuGrid();
		RuleType appliedRule = RuleType.getFromString(ruleType);
		GridSolver.applyRule(cleanedGrid, appliedRule);
		
		return cleanedGrid;
	}

	@PostMapping (value = "/solutionStepsMap")
	public HashMap<String, List<SolutionStep>> postSolutionStepsMap(@RequestBody SudokuGrid sudokuGridBack) {
		SudokuGrid cleanedGrid = sudokuGridBack.cloneSudokuGrid();
		return GridSolver.getSolutionStepsMap(cleanedGrid);
	}

	@PostMapping (value = "/solveGrid")
	public SudokuGrid postSolveGrid(@RequestBody SudokuGrid sudokuGridBack) {
		SudokuGrid solvedGrid = sudokuGridBack.cloneSudokuGrid();
		GridSolver.solveGrid(solvedGrid, Difficulty.FIVE);
		
		return solvedGrid;
	}
	
	@PostMapping (value = "/saveGrid")
	public ResponseEntity<?> postSaveGrid(@RequestBody SudokuGrid sudokuGridBack) {
		SudokuGrid cleanedGrid = sudokuGridBack.cloneSudokuGrid();
		String[] stringGrids = GridHelper.gridToString(cleanedGrid, '0');

		BaseGrid baseGrid = baseGridService.getBaseGridByStringGrid(stringGrids[0]);
		if (baseGrid == null) {
			baseGrid = new BaseGrid();
			baseGrid.setStringGrid(stringGrids[0]);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			Player player = playerService.getPlayerByEmail(email);
			playerService.savePlayer(player);

			PlayedGrid playedGrid = new PlayedGrid();
			playedGrid.setDifficulty(cleanedGrid.getDifficulty());
			playedGrid.setStringGrid(stringGrids[1]);
			playedGrid.setBaseGrid(baseGrid);
			playedGrid.setPlayer(player);
			playedGridService.savePlayedGrid(playedGrid);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping (value = "/loadGrid")
	public SudokuGrid getLoadGrid() {
		PlayedGrid playedGrid = new PlayedGrid();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			Player player = playerService.getPlayerByEmail(email);
			List<PlayedGrid> playedGrids = playedGridService.getPlayersGrids(player);
			playedGrid = playedGrids.get(playedGrids.size() - 1);
			
			String[] stringGrids = new String[2];
			stringGrids[0] = playedGrid.getBaseGrid().getStringGrid();
			stringGrids[1] = playedGrid.getStringGrid();
			SudokuGrid sudokuGrid = GridHelper.stringToGrid(stringGrids, '0');
			
			return sudokuGrid;
		}
		
		return sudokuGrid;
	}
	
}
