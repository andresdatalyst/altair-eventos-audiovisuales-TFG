package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.andrespr.springboot.app.models.Producer;
import com.andrespr.springboot.app.models.repository.IProducerRepository;


@Service
public class IProducerServiceImpl implements IProducerService{

	@Autowired
	private IProducerRepository producerService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Producer> findAll() {
		
		return (List<Producer>) producerService.findAll();
	}
	@Transactional
	@Override
	public void save(Producer producer) {
		
		producerService.save(producer);
	}
	@Transactional(readOnly = true)
	@Override
	public Producer findOne(Long id) {
		
		return producerService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		producerService.deleteById(id);
		
	}
	@Override
	public Page<Producer> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return producerService.findAll(pageable);
	}
	@Override
	public List<Producer> findByname(String busqueda) {
		// TODO Auto-generated method stub
		return producerService.findByname(busqueda);
	}

}
