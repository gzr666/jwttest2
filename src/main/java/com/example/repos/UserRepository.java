package com.example.repos;

import javax.persistence.NamedEntityGraph;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.User;

public interface UserRepository extends JpaRepository<User, Long>
{
	
	@EntityGraph(value = "GroupInfo.detail", type = EntityGraphType.LOAD)
	
	public User findByUsername(String username);
	
	
	
	
	
	 

	
}
