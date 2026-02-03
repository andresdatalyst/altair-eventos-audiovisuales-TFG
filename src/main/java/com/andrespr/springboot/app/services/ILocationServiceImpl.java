package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrespr.springboot.app.models.Location;
import com.andrespr.springboot.app.models.repository.ILocationRepository;

@Service
public class ILocationServiceImpl implements ILocationService {

	@Autowired
	private ILocationRepository locationService;

	@Transactional(readOnly = true)
	@Override
	public List<Location> findAll() {

		return (List<Location>) locationService.findAll();
	}

	@Transactional
	@Override
	public void save(Location location) {

		locationService.save(location);
	}

	@Transactional(readOnly = true)
	@Override
	public Location findOne(Long id) {

		return locationService.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		locationService.deleteById(id);

	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return locationService.findAll(pageable);
	}

	@Override
	public List<Location> findBylocationName(String busqueda) {
		// TODO Auto-generated method stub
		return locationService.findBylocationName(busqueda);
	}

	@Override
	public List<Location> findBycity(String city) {
		// TODO Auto-generated method stub
		return locationService.findBycity(city);
	}

}
