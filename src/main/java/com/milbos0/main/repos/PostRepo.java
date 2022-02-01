package com.milbos0.main.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milbos0.main.models.Post;

//Data Repository (Spring Data JPA)

@Repository
public interface PostRepo extends CrudRepository<Post, Long> {
	List<Post> findAll();
	
	//List<Post> findByDateRange(Date start, Date end);
}

/*
 * Data repositories are where we gain access to all our data once we start
 * talking with the database. What does that mean though?
 * 
 * Eric Evans of Domain Driven Design:
 * "A Repository represents all objects of a certain type as a conceptual set. "
 * "It acts like a collection, except with more elaborate querying capability."
 */


/* https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
 *  * https://www.baeldung.com/spring-data-derived-queries
 *  * 
 * List<Book> findByDescriptionContaining(String search); 
 * // this method counts how many titles contain a certain string 
 * Long countByTitleContaining(String search); 
 * // this method deletes a book that starts with a specific title 
 * Long deleteByTitleStartingWith(String search);
 */

/*
 * This will create a repository for us that will expose the Spring Data JPA.
 * This will expose a variety of methods, as well as allow us to extend it.
 * Without any further code, this will automatically allow us to create, read,
 * update, and destroy our books; after all, it is a CRUD repository we are
 * extending.
 * 
 * The Spring Data JPA Query creation can seem magical at first, and it takes
 * some experience writing in it to get a good feel for the queries, but it will
 * automatically build queries from method names in your interface.
 */

/*
 * The Collection in Java is a framework that provides an architecture to store
 * and manipulate the group of objects.
 * 
 * Java Collections can achieve all the operations that you perform on a data
 * such as searching, sorting, insertion, manipulation, and deletion.
 * 
 * Java Collection means a single unit of objects. Java Collection framework
 * provides many interfaces (Set, List, Queue, Deque) and classes (ArrayList,
 * Vector, LinkedList, PriorityQueue, HashSet, LinkedHashSet, TreeSet).
 */