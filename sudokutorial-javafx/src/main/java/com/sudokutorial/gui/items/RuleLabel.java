package com.sudokutorial.gui.items;

import com.sudokutorial.generator.items.RuleType;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class RuleLabel extends Label {

	private RuleType ruleType;
	private static final int MIN_SIZE = 40;

	public RuleLabel(RuleType ruleType) {
		super();

		this.ruleType = ruleType;
		setMinSize(MIN_SIZE, MIN_SIZE);
		setAlignment(Pos.CENTER);
	}

	public RuleLabel(String text, RuleType ruleType) {
		super(text);

		this.ruleType = ruleType;
		setMinSize(MIN_SIZE, MIN_SIZE);
		setAlignment(Pos.CENTER);
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}

}
