package com.sudokutorial.model.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "verification")
public class VerificationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "verification_id")
	private Long verificationId;

	@Column(name = "token")
	private String token;

	@OneToOne(targetEntity = Player.class, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "player_id", nullable = false)
	private Player player;

	@Column(name = "expiry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiry;

	@PrePersist
	private void setDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 24);
		expiry = c.getTime();
	}

	public VerificationToken() {

	}

	public VerificationToken(String token, Player player) {
		this.token = token;
		this.player = player;
	}

	public Long getId() {
		return verificationId;
	}

	public void setId(Long id) {
		this.verificationId = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + verificationId + ", token=" + token + ", player=" + player + ", expiry=" + expiry + "]";
	}
}
