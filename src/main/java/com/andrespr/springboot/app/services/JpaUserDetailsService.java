package com.andrespr.springboot.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrespr.springboot.app.models.UserAccount;

import com.andrespr.springboot.app.models.repository.IUserAccountRepository;
import com.andrespr.springboot.app.utilities.Util;

//Clase para encontrar el usuario
@Service
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserAccountRepository usuRepo;
	
	@Autowired
	private IRoleHasMenuService roleHasMenuService;
	
	private Logger logger;
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 UserAccount usuario = usuRepo.findByUsername(username);
		if(usuario == null) {
	           logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
	            throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
	        }
	        
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        
	      
	        authorities.add(new SimpleGrantedAuthority(usuario.getRole().getRoleName()));
	        
//	        Util.listMenu = roleHasMenuService.findAll()
//					 .stream()
//					 .filter(rolehasmenu ->rolehasmenu.getRole().getId() == usuario.getRole().getId())
//					 .map(rolehasmenu -> rolehasmenu.getMenu()).collect(Collectors.toList());
	        
	        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getActive(), true, true, true, authorities);
	}

}
