package com.andrespr.springboot.app.models.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Event;

public interface IEventRepository extends PagingAndSortingRepository<Event, Long> {

	public List<Event> findByeventName(String busqueda);

	public List<Event> findByEventDateBetween(Date fecha1, Date fecha2);
}
