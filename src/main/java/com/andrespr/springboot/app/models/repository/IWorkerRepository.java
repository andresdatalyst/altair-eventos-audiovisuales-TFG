package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Worker;

public interface IWorkerRepository extends PagingAndSortingRepository<Worker, Long> {

	public List<Worker> findByname(String busqueda);
}
