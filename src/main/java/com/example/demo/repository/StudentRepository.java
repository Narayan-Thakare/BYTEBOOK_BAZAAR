package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Admin;


public interface StudentRepository extends JpaRepository<Admin, Integer>{
	
	Admin findByUname(String username);

}
