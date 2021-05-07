package com.sudokutorial.generator;

import com.sudokutorial.generator.items.Difficulty;
import com.sudokutorial.generator.items.SudokuGrid;

/**
 *
 */
public class SudokuMain {

	public static void main(String[] args) {

		Difficulty difficulty = Difficulty.ONE;

		SudokuGrid sudokuGrid = new SudokuGrid();
		GridHelper.generateGrid(sudokuGrid, difficulty);
		GridHelper.printGrid(sudokuGrid.getArrayGrid());
		GridSolver.solveGrid(sudokuGrid, difficulty, true);
		GridHelper.printGrid(sudokuGrid.getArrayGrid());
	}
}
