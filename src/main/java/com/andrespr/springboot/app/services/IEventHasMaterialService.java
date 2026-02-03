package com.andrespr.springboot.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.AudiovisualMaterial;
import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.EventHasMaterial;




public interface IEventHasMaterialService {
	
	public List<EventHasMaterial> findAll();	
	public void save(EventHasMaterial eventHasMaterial);
	public EventHasMaterial findOne(Long id);
	public Page<EventHasMaterial> findAll(Pageable pageable);
	public void delete(Long id);
	
	
}
