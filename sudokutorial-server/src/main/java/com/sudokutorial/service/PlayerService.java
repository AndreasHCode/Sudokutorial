package com.sudokutorial.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sudokutorial.model.entity.Player;
import com.sudokutorial.model.entity.VerificationToken;
import com.sudokutorial.model.repository.PlayerDao;
import com.sudokutorial.model.repository.VerificationDao;

@Service
public class PlayerService implements UserDetailsService {

	@Autowired
	private PlayerDao playerDao;
	
	@Autowired
	private VerificationDao verificationDao;

	public void savePlayer(Player player) {
		playerDao.save(player);
	}
	
	public Player getPlayerByEmail(String email) {
		return playerDao.findByEmail(email);
	}

	public String createEmailVerificationToken(Player player) {
		VerificationToken token = new VerificationToken(UUID.randomUUID().toString(), player);
		verificationDao.save(token);
		return token.getToken();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Player player = playerDao.findByEmail(email);

		if (player == null) {
			throw new UsernameNotFoundException("No User found with Email: " + email);
		}

		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(player.getRole());
		String password = player.getPassword();

		return new User(email, password, true, true, true, true, auth);
	}
	
	public VerificationToken getVerificationtoken(String token) {
		return verificationDao.findByToken(token);
	}
	
	public void deleteVerificationToken(VerificationToken token) {
		verificationDao.delete(token);
	}

}
