package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ClientData;

public interface ClientRpo extends JpaRepository<ClientData, Integer> {

}
