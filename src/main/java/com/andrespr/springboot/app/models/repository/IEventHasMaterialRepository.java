package com.andrespr.springboot.app.models.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.EventHasMaterial;

public interface IEventHasMaterialRepository extends PagingAndSortingRepository<EventHasMaterial, Long> {

}
