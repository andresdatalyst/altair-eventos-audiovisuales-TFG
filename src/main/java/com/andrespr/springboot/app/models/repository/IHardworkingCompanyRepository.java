package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.HardworkingCompany;

public interface IHardworkingCompanyRepository extends PagingAndSortingRepository<HardworkingCompany, Long> {

	public List<HardworkingCompany> findByname(String busqueda);

	public List<HardworkingCompany> findByprovince(String province);
}
