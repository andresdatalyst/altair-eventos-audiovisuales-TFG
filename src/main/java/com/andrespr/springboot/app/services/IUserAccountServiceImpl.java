package com.andrespr.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.andrespr.springboot.app.models.UserAccount;
import com.andrespr.springboot.app.models.repository.IUserAccountRepository;


@Service
public class IUserAccountServiceImpl implements IUserAccountService{

	@Autowired
	private IUserAccountRepository userAccountService;
	
	@Transactional(readOnly = true)
	@Override
	public List<UserAccount> findAll() {
		
		return (List<UserAccount>) userAccountService.findAll();
	}
	@Transactional
	@Override
	public void save(UserAccount userAccount) {
		
		userAccountService.save(userAccount);
	}
	@Transactional(readOnly = true)
	@Override
	public UserAccount findOne(Long id) {
		
		return userAccountService.findById(id).orElse(null);
	}
	@Transactional
	@Override
	public void delete(Long id) {
		userAccountService.deleteById(id);
		
	}
	@Override
	public Page<UserAccount> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return userAccountService.findAll(pageable);
	}
	@Override
	public List<UserAccount> findByEmail(String email) {
		// TODO Auto-generated method stub
		return userAccountService.findByEmail(email);
	}
	@Override
	public List<UserAccount> findByusername(String username) {
		// TODO Auto-generated method stub
		return userAccountService.findByusername(username);
	}
	
	
	

}
