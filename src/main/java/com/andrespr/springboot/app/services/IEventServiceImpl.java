package com.andrespr.springboot.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.repository.IEventRepository;


@Service
public class IEventServiceImpl implements IEventService{

	@Autowired
	private IEventRepository eventService;
	
	@Transactional(readOnly = true)
	@Override
	public List<Event> findAll() {
		
		return (List<Event>) eventService.findAll();
	}
	@Transactional
	@Override
	public void save(Event event) {
		
		eventService.save(event);
	}
	@Transactional(readOnly = true)
	@Override
	public Event findOne(Long id) {
		
		return eventService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		eventService.deleteById(id);
		
	}
	@Override
	public Page<Event> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return eventService.findAll(pageable);
	}
	@Override
	public List<Event> findByeventName(String busqueda) {
		// TODO Auto-generated method stub
		return eventService.findByeventName(busqueda);
	}
	@Override
	public List<Event> findByEventDateBetween(Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return eventService.findByEventDateBetween(fecha1, fecha2);
	}

}
