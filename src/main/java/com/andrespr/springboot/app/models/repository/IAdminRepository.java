package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Admin;

public interface IAdminRepository extends PagingAndSortingRepository<Admin, Long> {

	public List<Admin> findByname(String busqueda);
}
