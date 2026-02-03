package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.Admin;
import com.andrespr.springboot.app.models.RoleHasMenu;



public interface IRoleHasMenuService {
	
	public List<RoleHasMenu> findAll();	
	public Page<RoleHasMenu> findAll(Pageable pageable);
	public void save(RoleHasMenu roleHasMenu);
	public RoleHasMenu findOne(Long id);
	public void delete(Long id);
}
