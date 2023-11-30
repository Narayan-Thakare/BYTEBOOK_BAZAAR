package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Book;
import com.example.demo.model.History;

public interface HistoryRepo extends JpaRepository<History, Integer> {

	
	
    List<Book> findAllByQuantityLessThanEqual(int quantity, Sort sort);
	
	
	
}
