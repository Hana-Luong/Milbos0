package com.milbos0.main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milbos0.main.models.Post;
import com.milbos0.main.repos.PostRepo;

/*Services are the business logic of our application.
In order to make sure we don't DRY( Don't Repeat Yourself), we will create a service layer. 
We want skinny controllers and fat models (from MVC).*/

/*the @Service will allows Spring to inject this class as a dependency in any controller. 
Also, to access the database, we are going to need to use dependency injection for the 
repository.*/

@Service
public class PostService {
	
	@Autowired
	private PostRepo postRepo;  		
	
	public List<Post> allPosts(){
		return postRepo.findAll();
	}
	
	public Post save(Post i) {
		return postRepo.save(i);
	}
	
	public Post findById(Long id) {
		Optional<Post> post = postRepo.findById(id);
		
		if(post.isPresent()) {
			return post.get();
		}else {
			return null;
		}
	}
	
	public void delete(Long id) {
		postRepo.deleteById(id);
	}

	//What is it for? 
	public void deletePost(Long id) {
		// TODO Auto-generated method stub???
		
	}	
}

/* Dependency:
 * Whenever a class A uses another class or interface B, then A depends on B. A
 * cannot carry out its work without B, and A cannot be reused without also
 * reusing B. In such a situation the class A is called the "dependant" and the
 * class or interface B is called the "dependency". A dependant depends on its
 * dependencies.
 */

/*
 * With Spring Boot, your microservices can start small and iterate fast. That’s
 * why it has become the de facto standard for Java™ microservices.
 * microservice = a small app;
 * No need to use JEE in Spring boot. 
 */
