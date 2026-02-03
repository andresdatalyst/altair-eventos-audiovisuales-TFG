package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




import com.andrespr.springboot.app.models.Worker;
import com.andrespr.springboot.app.models.repository.IWorkerRepository;


@Service
public class IWorkerServiceImpl implements IWorkerService{

	@Autowired
	private IWorkerRepository workerService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Worker> findAll() {
		
		return (List<Worker>) workerService.findAll();
	}
	@Transactional
	@Override
	public void save(Worker worker) {
		
		workerService.save(worker);
	}
	@Transactional(readOnly = true)
	@Override
	public Worker findOne(Long id) {
		
		return workerService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		workerService.deleteById(id);
		
	}
	@Override
	public Page<Worker> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return workerService.findAll(pageable);
	}
	@Override
	public List<Worker> findByname(String busqueda) {
		// TODO Auto-generated method stub
		return workerService.findByname(busqueda);
	}

}
