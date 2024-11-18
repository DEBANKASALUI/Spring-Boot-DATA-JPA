package com.jpa.test;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.jpa.test.dao.UserRepository;
import com.jpa.test.entity.User;

@SpringBootApplication
public class SpringBootJpAexampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootJpAexampleApplication.class, args);
		UserRepository userRepo = context.getBean(UserRepository.class);

//		SAVE/INSERT DATA into DB:

//		Creating Object of User
		User user=new User();
		user.setName("Sirius Potter");
		user.setCity("Greece");

//		Saving single Object: save()
		User suser = userRepo.save(user);
		System.out.println("User Saved Successfully!");
		System.out.println(suser);

//		Saving multiple Objects: saveAll()
		User user1=new User();
		user1.setName("Harry Potter");
		user1.setCity("Greece");
		
		User user2=new User();
		user2.setName("James Potter");
		user2.setCity("London");
		
		List<User> users = List.of(user1,user2);
		Iterable<User> result = userRepo.saveAll(users);
		result.forEach(u->System.out.println(u));
		System.out.println("Users Saved successfully!...");

		
		
//		UPDATE DATA in DB based on ID:
		Optional<User> optional = userRepo.findById(14);
		optional.ifPresentOrElse(element -> {
			System.out.println("User found: " + element);
			element.setCity("USA");
			userRepo.save(element);
			System.out.println("User Data Updated Successfully: " + element);
		}, () -> System.out.println("User is not present in DB"));

//		Alternate way: optional.get():No such Element exception will be thrown		
//		User upUser = optional.get();
//		upUser.setCity("France");
//		userRepo.save(upUser);
//		System.out.println("User Data Updated Successfully"+ upUser);

		
		
		
//		GET/SELECT Single DATA from DB: use findById()

//		GET/SELECT ALL DATA from DB:
		Iterable<User> itr = userRepo.findAll();
		itr.forEach(e -> System.out.println(e));

//		Iterate using Consumer Anonymous Class:
		itr.forEach(new Consumer<User>() {
			@Override
			public void accept(User t) {
				System.out.println(t);
			}
			});

		
		
		
//		DELETE single DATA from DB based on ID:
		Optional<User> optionalUser = userRepo.findById(13);
		if (optionalUser.isPresent()) {
		    userRepo.deleteById(13);
		    System.out.println("User has been deleted successfully..");
		} else {
		    System.out.println("User with given ID is not present in the database.");
		}

		
//		DELETE ALL DATA from DB:
		Iterable<User> allUsers = userRepo.findAll();
		userRepo.deleteAll(allUsers);
		allUsers.forEach(e->System.out.println(e));
		System.out.println("All Users have been deleted successfully..");
	
		
		
//		Custom Finder Methods/Derived Query Methods
		System.out.println(userRepo.findByName("Harry Potter"));
		System.out.println(userRepo.findByCity("Greenland"));
		System.out.println(userRepo.findByNameAndCity("Harry Potter", "Greece"));
		System.out.println(userRepo.findByNameStartingWith("J"));
		
		
//		Executing JPQL
		List<User> userList=userRepo.getAllUsers();
		userList.forEach(e->System.out.println(e));
		
//		Parameterised JPQL
		List<User> usersByName=userRepo.getUserByName("Harry Potter");
		usersByName.forEach(e->System.out.println(e));
		
		List<User> usersByNameandCity=userRepo.getUserByNameAndCity("Harry Potter","Greece");
		usersByNameandCity.forEach(e->System.out.println(e));
		
//		Native Queries
		List<User> nativeuserList=userRepo.getUsers();
		nativeuserList.forEach(e->System.out.println(e));
		
	}

}
