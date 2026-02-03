package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrespr.springboot.app.models.Menu;
import com.andrespr.springboot.app.models.repository.IMenuRepository;

@Service
public class IMenuServiceImpl implements IMenuService{

	@Autowired
	private IMenuRepository menuService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Menu> findAll() {
		
		return (List<Menu>) menuService.findAll();
	}
	@Transactional
	@Override
	public void save(Menu menu) {
		
		menuService.save(menu);
	}
	@Transactional(readOnly = true)
	@Override
	public Menu findOne(Long id) {
		
		return menuService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		menuService.deleteById(id);
		
	}
	@Override
	public Page<Menu> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return menuService.findAll(pageable);
	}
	
	

}
