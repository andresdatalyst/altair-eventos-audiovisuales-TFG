package com.andrespr.springboot.app.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.RoleHasMenu;

public interface IRoleHasMenuRepository extends PagingAndSortingRepository<RoleHasMenu, Long> {

}
