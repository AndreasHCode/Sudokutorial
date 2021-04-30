package com.sudokutorial.generator.items;

import java.util.List;

public class SolutionStep {

	private Cell cell;
	private RuleType ruleType;
	private int entry;
	private List<Cell> reason;
	private String explanation;

	public SolutionStep() {

	}

	public SolutionStep(Cell cell, int entry, List<Cell> reason, RuleType ruleType, String explanation) {
		this.cell = cell;
		this.entry = entry;
		this.reason = reason;
		this.ruleType = ruleType;
		this.explanation = explanation;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public List<Cell> getReason() {
		return reason;
	}

	public void setReason(List<Cell> reason) {
		this.reason = reason;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

}