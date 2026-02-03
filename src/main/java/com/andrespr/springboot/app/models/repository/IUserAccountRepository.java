package com.andrespr.springboot.app.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andrespr.springboot.app.models.UserAccount;

public interface IUserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {

	// No es necesario hacer la query, lo hace a través del nombre del método
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	public UserAccount findByUsername(String username);

	public List<UserAccount> findByEmail(String email);

	public List<UserAccount> findByusername(String username);
}
