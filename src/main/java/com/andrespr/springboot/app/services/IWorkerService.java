package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.Worker;

public interface IWorkerService {

	public List<Worker> findAll();	
	public Page<Worker> findAll(Pageable pageable);	
	public void save(Worker worker);
	public Worker findOne(Long id);
	public void delete(Long id);
	
	public List<Worker> findByname(String busqueda);
}
