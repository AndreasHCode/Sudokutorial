package com.sudokutorial.controller.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sudokutorial.model.entity.Player;
import com.sudokutorial.model.entity.VerificationToken;
import com.sudokutorial.model.event.OnRegistrationCompleteEvent;
import com.sudokutorial.service.PlayerService;

@RestController
public class RegistryController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	private PlayerService playerService;

	@PutMapping(value = "/registerplayer")
	public ResponseEntity<?> registerplayerPut(HttpServletRequest request, @RequestBody Player player) {
		logger.info("Saving new Player: " + player.getFirstname());

		player.setPassword(new BCryptPasswordEncoder().encode(player.getPassword()));
		player.setRole("ROLE_USER");

		try {
			playerService.savePlayer(player);
		} catch (DataAccessException de) {
			return new ResponseEntity<>("", HttpStatus.OK);
		}

		String token = playerService.createEmailVerificationToken(player);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		OnRegistrationCompleteEvent event = new OnRegistrationCompleteEvent(player.getEmail(), token, url);
		eventPublisher.publishEvent(event);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/confirmregistration")
	public void confirmRegistration(HttpServletResponse response, @RequestParam("t") String tokenString)
			throws IOException {
		VerificationToken token = playerService.getVerificationtoken(tokenString);
		System.out.println(token);

		Player player = token.getPlayer();
		player.setEnabled(true);
		playerService.deleteVerificationToken(token);
		playerService.savePlayer(player);

		response.sendRedirect("/login");
	}

}
