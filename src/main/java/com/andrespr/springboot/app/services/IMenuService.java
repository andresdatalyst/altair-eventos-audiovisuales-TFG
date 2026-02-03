package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.Admin;
import com.andrespr.springboot.app.models.Location;
import com.andrespr.springboot.app.models.Menu;



public interface IMenuService {
	
	public List<Menu> findAll();	
	public Page<Menu> findAll(Pageable pageable);
	public void save(Menu menu);
	public Menu findOne(Long id);
	public void delete(Long id);
}
