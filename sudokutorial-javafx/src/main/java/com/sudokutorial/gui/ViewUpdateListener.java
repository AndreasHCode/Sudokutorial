package com.sudokutorial.gui;

import java.util.List;
import java.util.Set;

import com.sudokutorial.generator.items.RuleType;

public interface ViewUpdateListener {
	public void updateGrid();

	public void updateCell(int cellId, Set<Integer> possibleEntries);

	public void updateCell(int cellId, int number);

	public void showWrongEntry(int cellId);

	public void updateRuleLabel(String entry, RuleType ruleType);

	public void markRuleExclusion(int cellId, int entry, List<Integer> reasonIds);
}
