package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.andrespr.springboot.app.models.HardworkingCompany;
import com.andrespr.springboot.app.models.repository.IHardworkingCompanyRepository;


@Service
public class IHardworkingCompanyServiceImpl implements IHardworkingCompanyService{

	@Autowired
	private IHardworkingCompanyRepository hardworkingCompanyService;
	
	@Transactional(readOnly = true)
	@Override
	public List<HardworkingCompany> findAll() {
		
		return (List<HardworkingCompany>) hardworkingCompanyService.findAll();
	}
	@Transactional
	@Override
	public void save(HardworkingCompany hardworkingCompany) {
		
		hardworkingCompanyService.save(hardworkingCompany);
	}
	@Transactional(readOnly = true)
	@Override
	public HardworkingCompany findOne(Long id) {
		
		return hardworkingCompanyService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		hardworkingCompanyService.deleteById(id);
		
	}
	@Override
	public Page<HardworkingCompany> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return hardworkingCompanyService.findAll(pageable);
	}
	@Override
	public List<HardworkingCompany> findByname(String busqueda) {
		// TODO Auto-generated method stub
		return hardworkingCompanyService.findByname(busqueda);
	}
	@Override
	public List<HardworkingCompany> findByprovince(String province) {
		// TODO Auto-generated method stub
		return hardworkingCompanyService.findByprovince(province);
	}

}
