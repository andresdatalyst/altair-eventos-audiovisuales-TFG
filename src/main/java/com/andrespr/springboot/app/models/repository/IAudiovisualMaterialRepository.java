package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.AudiovisualMaterial;

public interface IAudiovisualMaterialRepository extends PagingAndSortingRepository<AudiovisualMaterial, Long> {

	public List<AudiovisualMaterial> findBymaterialName(String busqueda);
}
