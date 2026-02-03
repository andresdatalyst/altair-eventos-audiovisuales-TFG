package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.andrespr.springboot.app.models.RoleHasMenu;
import com.andrespr.springboot.app.models.repository.IRoleHasMenuRepository;


@Service
public class IRoleHasMenuServiceImpl implements IRoleHasMenuService{

	@Autowired
	private IRoleHasMenuRepository roleHasMenuService;
	
	@Transactional(readOnly = true)
	@Override
	public List<RoleHasMenu> findAll() {
		
		return (List<RoleHasMenu>) roleHasMenuService.findAll();
	}
	@Transactional
	@Override
	public void save(RoleHasMenu RoleHasMenu) {
		
		roleHasMenuService.save(RoleHasMenu);
	}
	@Transactional(readOnly = true)
	@Override
	public RoleHasMenu findOne(Long id) {
		
		return roleHasMenuService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		roleHasMenuService.deleteById(id);
		
	}
	@Override
	public Page<RoleHasMenu> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return roleHasMenuService.findAll(pageable);
	}

}
