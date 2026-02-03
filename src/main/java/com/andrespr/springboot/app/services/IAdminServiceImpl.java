package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrespr.springboot.app.models.Admin;
import com.andrespr.springboot.app.models.repository.IAdminRepository;



@Service
public class IAdminServiceImpl implements IAdminService{

	@Autowired
	private IAdminRepository adminService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Admin> findAll() {
		
		return (List<Admin>) adminService.findAll();
	}
	@Transactional
	@Override
	public void save(Admin admin) {
		
		adminService.save(admin);
	}
	@Transactional(readOnly = true)
	@Override
	public Admin findOne(Long id) {
		
		return adminService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		adminService.deleteById(id);
		
	}
	@Override
	public Page<Admin> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return adminService.findAll(pageable);
	}
	@Override
	public List<Admin> findByname(String busqueda) {
		// TODO Auto-generated method stub
		return adminService.findByname(busqueda);
	}

}
