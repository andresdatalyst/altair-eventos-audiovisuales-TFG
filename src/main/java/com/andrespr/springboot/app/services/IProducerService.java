package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.Location;
import com.andrespr.springboot.app.models.Producer;



public interface IProducerService {
	
	public List<Producer> findAll();
	public Page<Producer> findAll(Pageable pageable);
	public void save(Producer producer);
	public Producer findOne(Long id);
	public void delete(Long id);
	
	public List<Producer> findByname(String busqueda);
}
