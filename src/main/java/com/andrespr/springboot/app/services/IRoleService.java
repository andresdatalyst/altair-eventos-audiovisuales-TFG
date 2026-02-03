package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.Role;
import com.andrespr.springboot.app.models.RoleHasMenu;



public interface IRoleService {
	
	public List<Role> findAll();
	public Page<Role> findAll(Pageable pageable);
	public void save(Role role);
	public Role findOne(Long id);
	public void delete(Long id);
}
