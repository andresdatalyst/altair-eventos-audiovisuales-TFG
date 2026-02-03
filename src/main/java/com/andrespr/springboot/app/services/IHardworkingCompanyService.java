package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.HardworkingCompany;



public interface IHardworkingCompanyService {
	
	public List<HardworkingCompany> findAll();	
	public Page<HardworkingCompany> findAll(Pageable pageable);
	public void save(HardworkingCompany hardworkingCompany);
	public HardworkingCompany findOne(Long id);
	public void delete(Long id);
	
	public List<HardworkingCompany> findByname(String busqueda);
	public List<HardworkingCompany> findByprovince(String province);
}
