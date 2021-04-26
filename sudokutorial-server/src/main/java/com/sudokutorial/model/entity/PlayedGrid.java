package com.sudokutorial.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sudokutorial.generator.items.Difficulty;

@Entity
@Table (name = "playedgrid")
public class PlayedGrid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "playedgrid_id", nullable = false)
	private Long playedGridId;

	@Column(name = "stringgrid", length = 81)
	private String stringGrid;
	
	@Column(name = "difficulty", length = 30)
	private Difficulty difficulty;
	
	@Column(name = "solved")
	private boolean solved;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "basegrid_id", nullable = false)
	private BaseGrid baseGrid;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id", nullable = false)
	private Player player;

	public PlayedGrid() {
		super();
	}

	public PlayedGrid(Long playedGrid_id, String stringGrid, Difficulty difficulty, boolean solved, BaseGrid baseGrid) {
		super();
		this.playedGridId = playedGrid_id;
		this.stringGrid = stringGrid;
		this.difficulty = difficulty;
		this.solved = solved;
		this.baseGrid = baseGrid;
	}

	public Long getPlayedGridId() {
		return playedGridId;
	}

	public void setPlayedGridId(Long playedGridId) {
		this.playedGridId = playedGridId;
	}

	public String getStringGrid() {
		return stringGrid;
	}

	public void setStringGrid(String stringGrid) {
		this.stringGrid = stringGrid;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public BaseGrid getBaseGrid() {
		return baseGrid;
	}

	public void setBaseGrid(BaseGrid baseGrid) {
		this.baseGrid = baseGrid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "PlayedGrid [playedGridId=" + playedGridId + ", stringGrid=" + stringGrid + ", difficulty=" + difficulty
				+ ", solved=" + solved + ", baseGrid=" + baseGrid + ", player=" + player + "]";
	}

	
}
