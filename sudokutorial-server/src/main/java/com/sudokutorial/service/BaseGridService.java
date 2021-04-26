package com.sudokutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudokutorial.model.entity.BaseGrid;
import com.sudokutorial.model.repository.BaseGridDao;

@Service
public class BaseGridService {

	@Autowired
	BaseGridDao baseGridDao;
	
	public BaseGrid getBaseGridByStringGrid(String stringGrid) {
		return baseGridDao.findByStringGrid(stringGrid);
	}
	
}
