package com.andrespr.springboot.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.AudiovisualMaterial;
import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.EventHasMaterial;



public interface IEventService {
	
	public List<Event> findAll();	
	public Page<Event> findAll(Pageable pageable);
	public void save(Event event);
	public Event findOne(Long id);
	public void delete(Long id);
	public List<Event> findByeventName(String busqueda);
	public List<Event> findByEventDateBetween(Date fecha1, Date fecha2);
}
