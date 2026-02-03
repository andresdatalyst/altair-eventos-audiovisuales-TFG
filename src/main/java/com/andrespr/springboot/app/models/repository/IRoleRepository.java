package com.andrespr.springboot.app.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.Role;

public interface IRoleRepository extends PagingAndSortingRepository<Role, Long> {

}
