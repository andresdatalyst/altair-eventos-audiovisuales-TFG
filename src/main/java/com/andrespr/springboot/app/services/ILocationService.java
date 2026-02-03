package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.HardworkingCompany;
import com.andrespr.springboot.app.models.Location;



public interface ILocationService {
	
	public List<Location> findAll();
	public Page<Location> findAll(Pageable pageable);
	public void save(Location location);
	public Location findOne(Long id);
	public void delete(Long id);
	
	public List<Location> findBylocationName(String busqueda);
	public List<Location> findBycity(String city);
}
