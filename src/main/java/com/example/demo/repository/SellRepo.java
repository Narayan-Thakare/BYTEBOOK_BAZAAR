package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Sellbooks;

public interface SellRepo extends JpaRepository<Sellbooks, Integer> {
	
	
	
	  @Query("SELECT SUM(s.rate * s.newQuantity) FROM Sellbooks s")
	    Double calculateTotal();

	  @Query("SELECT s.title FROM Sellbooks s")
	  List<String> getTitles();

	
	
	
	

}
