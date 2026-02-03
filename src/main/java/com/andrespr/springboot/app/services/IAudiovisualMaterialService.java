package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.AudiovisualMaterial;




public interface IAudiovisualMaterialService {
	
	public List<AudiovisualMaterial> findAll();	
	public void save(AudiovisualMaterial audiovisualMaterial);
	public Page<AudiovisualMaterial> findAll(Pageable pageable);
	public AudiovisualMaterial findOne(Long id);
	public void delete(Long id);
	
	public List<AudiovisualMaterial> findBymaterialName(String busqueda);
}
