package com.andrespr.springboot.app.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Menu;

public interface IMenuRepository extends PagingAndSortingRepository<Menu, Long> {

}
