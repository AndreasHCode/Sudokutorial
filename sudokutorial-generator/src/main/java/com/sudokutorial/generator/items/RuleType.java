package com.sudokutorial.generator.items;

public enum RuleType {

	EXCLUDE_ENTRIES, 
	UNIQUE_ENTRY, 
	UNIQUE_ROW_COLUMN, 
	ENTRY_COMBINATION, 
	X_WING, 
	SWORDFISH, 
	JELLYFISH, 
	REMOTE_PAIRS, 
	UNIQUE_RECTANGLE;

	public static RuleType getFromString(String stringRule) {
		if (stringRule.equals("removeOptions")) {
			return EXCLUDE_ENTRIES;
		} else if (stringRule.equals("findUnique")) {
			return UNIQUE_ENTRY;
		} else if (stringRule.equals("findUniqueRowColumn")) {
			return UNIQUE_ROW_COLUMN;
		} else if (stringRule.equals("entryCombination")) {
			return ENTRY_COMBINATION;
		} else if (stringRule.equals("findXWing")) {
			return X_WING;
		} else if (stringRule.equals("findSwordfish")) {
			return SWORDFISH;
		} else if (stringRule.equals("findJellyfish")) {
			return JELLYFISH;
		} else if (stringRule.equals("findRemotePairs")) {
			return REMOTE_PAIRS;
		} else if (stringRule.equals("findUniqueRectangle")) {
			return UNIQUE_RECTANGLE;
		}
		
		return EXCLUDE_ENTRIES;
	}
	
	public static String getAsString(RuleType ruleType) {
		if (ruleType == EXCLUDE_ENTRIES) {
			return "removeOptions";
		} else if (ruleType == UNIQUE_ENTRY) {
			return "findUnique";
		} else if (ruleType == UNIQUE_ROW_COLUMN) {
			return "findUniqueRowColumn";
		} else if (ruleType == ENTRY_COMBINATION) {
			return "entryCombination";
		} else if (ruleType == X_WING) {
			return "findXWing";
		} else if (ruleType == SWORDFISH) {
			return "findSwordfish";
		} else if (ruleType == JELLYFISH) {
			return "findJellyfish";
		} else if (ruleType == REMOTE_PAIRS) {
			return "findRemotePairs";
		} else if (ruleType == UNIQUE_RECTANGLE) {
			return "findUniqueRectangle";
		}
		
		return "removeOtions";
	}

}
