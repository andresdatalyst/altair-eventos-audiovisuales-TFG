package com.andrespr.springboot.app.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andrespr.springboot.app.models.UserAccount;



public interface IUserAccountService {
	
	public List<UserAccount> findAll();	
	public Page<UserAccount> findAll(Pageable pageable);	
	public void save(UserAccount userAccount);
	public UserAccount findOne(Long id);
	public void delete(Long id);
	
	public List<UserAccount> findByEmail(String email);
	public List<UserAccount> findByusername(String username);
	
}
