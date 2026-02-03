package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Producer;

public interface IProducerRepository extends PagingAndSortingRepository<Producer, Long> {

	public List<Producer> findByname(String busqueda);

}
