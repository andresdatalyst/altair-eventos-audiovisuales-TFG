package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.Admin;
import com.andrespr.springboot.app.models.AudiovisualMaterial;


public interface IAdminService {

	public List<Admin> findAll();	
	public Page<Admin> findAll(Pageable pageable);
	public void save(Admin admin);
	public Admin findOne(Long id);
	public void delete(Long id);
	
	public List<Admin> findByname(String busqueda);
}
