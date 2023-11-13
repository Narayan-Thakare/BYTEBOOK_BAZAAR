package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserDetailsImpliments;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private StudentRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin user=repo.findByUname(username);
		
		if(user==null)
			throw new UsernameNotFoundException("User not found");
		
		return new UserDetailsImpliments(user);
	}
	
}