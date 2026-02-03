package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Location;

public interface ILocationRepository extends PagingAndSortingRepository<Location, Long> {

	public List<Location> findBylocationName(String busqueda);

	public List<Location> findBycity(String city);
}
