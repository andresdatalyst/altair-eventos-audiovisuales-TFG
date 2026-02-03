package com.andrespr.springboot.app.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrespr.springboot.app.models.Event;
import com.andrespr.springboot.app.models.EventHasMaterial;
import com.andrespr.springboot.app.models.RoleHasMenu;
import com.andrespr.springboot.app.models.repository.IEventHasMaterialRepository;
import com.andrespr.springboot.app.models.repository.IRoleHasMenuRepository;


@Service
public class IEventHasMaterialServiceImpl implements IEventHasMaterialService{

	@Autowired
	private IEventHasMaterialRepository eventHasMaterialService;
	
	@Transactional(readOnly = true)
	@Override
	public List<EventHasMaterial> findAll() {
		
		return (List<EventHasMaterial>) eventHasMaterialService.findAll();
	}
	@Transactional
	@Override
	public void save(EventHasMaterial eventHasMaterial) {
		
		eventHasMaterialService.save(eventHasMaterial);
	}
	@Transactional(readOnly = true)
	@Override
	public EventHasMaterial findOne(Long id) {
		
		return eventHasMaterialService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		eventHasMaterialService.deleteById(id);
		
	}
	@Override
	public Page<EventHasMaterial> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return eventHasMaterialService.findAll(pageable);
	}
	

	
}
