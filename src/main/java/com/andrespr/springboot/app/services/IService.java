package com.andrespr.springboot.app.services;

import java.util.List;

import com.andrespr.springboot.app.models.AudiovisualMaterial;



public interface IService {
	
	public List<AudiovisualMaterial> findAll();	
	public void save(AudiovisualMaterial audiovisualMaterial);
	public AudiovisualMaterial findOne(Long id);
	public void delete(Long id);
}
