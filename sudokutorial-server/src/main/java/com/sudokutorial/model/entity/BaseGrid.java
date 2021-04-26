package com.sudokutorial.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "basegrid")
public class BaseGrid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "basegrid_id")
	private Long baseGridId;

	@Column(name = "stringgrid", length = 81, unique = true)
	private String stringGrid;

	public Long getBaseGridId() {
		return baseGridId;
	}

	public void setBaseGridId(Long baseGridId) {
		this.baseGridId = baseGridId;
	}

	public String getStringGrid() {
		return stringGrid;
	}

	public void setStringGrid(String stringGrid) {
		this.stringGrid = stringGrid;
	}
	
}
