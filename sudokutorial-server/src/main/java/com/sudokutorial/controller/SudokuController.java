package com.sudokutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sudokutorial.model.entity.Player;
import com.sudokutorial.service.PlayerService;

@Controller
public class SudokuController {

	@Autowired
	PlayerService playerService;

	@RequestMapping(value = "/sudoku")
	public ModelAndView sudoku(ModelAndView modelAndView) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// TODO make token verification requirement for safe

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			String email = auth.getName();
			Player player = playerService.getPlayerByEmail(email);
			modelAndView.getModel().put("playerName", player.getFirstname());
		}

		modelAndView.setViewName("sudoku");
		return modelAndView;
	}

}
