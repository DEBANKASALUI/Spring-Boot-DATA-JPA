package com.jpa.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jpa.test.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

//	Custom Finder Methods/Derived Query Methods
	public List<User> findByName(String name);

	public List<User> findByCity(String city);

	public List<User> findByNameAndCity(String name, String city);

	public List<User> findByNameStartingWith(String prefix);

//	Executing JPQL
	@Query("from User")
	public List<User> getAllUsers();

//	Parameterised JPQL
	@Query("Select u from User u where u.name= :n")
	public List<User> getUserByName(@Param("n") String name);

	@Query("Select u from User u where u.name= :n and u.city= :c")
	public List<User> getUserByNameAndCity(@Param("n") String name,@Param("c") String city);
	
//	Native Queries
	@Query(value="select * from user",nativeQuery=true)
	public List<User> getUsers();

}
