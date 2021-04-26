package com.sudokutorial.controller;

import java.util.HashMap;
import java.util.List;

import com.sudokutorial.generator.items.Cell;
import com.sudokutorial.generator.items.RuleType;
import com.sudokutorial.gui.items.RuleLabel;

public interface GridListener {
	public List<Cell> getListGridCopy();

	public List<Cell> getSolvedGridCopy();

	public void makeEntry(int number, int cellId);

	public void removeEntry(int cellId);

	public void removePossibleEntry(int number, int cellId);

	public void addPossibleEntry(int number, int cellId);

	public void applyRule(RuleType ruleType);

	public void solveGrid();

	public void makeUniqueEntries();

	public void getRuleExclusions(HashMap<RuleType, RuleLabel> ruleLabels);

	public void markRuleExclusion(RuleType ruleType);
}
