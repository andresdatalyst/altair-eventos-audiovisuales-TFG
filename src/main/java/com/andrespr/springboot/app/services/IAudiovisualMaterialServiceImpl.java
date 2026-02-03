package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrespr.springboot.app.models.AudiovisualMaterial;
import com.andrespr.springboot.app.models.repository.IAudiovisualMaterialRepository;


@Service
public class IAudiovisualMaterialServiceImpl implements IAudiovisualMaterialService{

	@Autowired
	private IAudiovisualMaterialRepository audiovisualMaterialService;
	
	@Transactional(readOnly = true)
	@Override
	public List<AudiovisualMaterial> findAll() {
		
		return (List<AudiovisualMaterial>) audiovisualMaterialService.findAll();
	}
	@Transactional
	@Override
	public void save(AudiovisualMaterial audiovisualMaterial) {
		
		audiovisualMaterialService.save(audiovisualMaterial);
	}
	@Transactional(readOnly = true)
	@Override
	public AudiovisualMaterial findOne(Long id) {
		
		return audiovisualMaterialService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		audiovisualMaterialService.deleteById(id);
		
	}
	@Override
	public Page<AudiovisualMaterial> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return audiovisualMaterialService.findAll(pageable);
	}
	@Override
	public List<AudiovisualMaterial> findBymaterialName(String busqueda) {
		// TODO Auto-generated method stub
		return audiovisualMaterialService.findBymaterialName(busqueda);
	}

}
