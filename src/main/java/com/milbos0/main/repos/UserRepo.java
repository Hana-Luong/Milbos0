package com.milbos0.main.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.milbos0.main.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
  User findByEmail(String email);  	
  List<User> findAll();				
  //can write more methods for "Search"; 
}

/*
 * a data access object (DAO) is a pattern that provides an abstract interface
 * to some type of database or other persistence mechanism. 
 * By mapping application calls to the persistence layer, the DAO provides some 
 * specific data operations without exposing details of the database. 
 * This isolation supports the single responsibility principle. 
 * It separates what data access the application needs, 
 * in terms of domain-specific objects and data types (the public interface of the DAO), 
 * from how these needs can be satisfied with a specific DBMS, database schema, etc. 
 * (the implementation of the DAO).
 */

/*
 * Persistence = Store Permanently.
 * 
 * In JAVA we work with Objects and try to store Object's values into database(RDBMS mostly). 
 * JPA provides implementation for Object Relation Mapping(ORM) ,
 * so that we can directly store Object into Database as a new Tuple. 
 * Object, in JPA, are converted to Entity for mapping it to the Table in Database. 
 * So Persisting an Entity means Permanently Storing Object(Entity) into Database.
 */

/*
 * A tuple is a collection of several elements that may or may not be related to
 * each other. In other words, tuples can be considered anonymous objects.
 *  
 * Unit<A>
 * Pair<A,B> 
 * Triplet<A,B,C> 
 * Quartet<A,B,C,D> 
 * Quintet<A,B,C,D,E>
 * Sextet<A,B,C,D,E,F> 
 * Septet<A,B,C,D,E,F,G> 
 * Octet<A,B,C,D,E,F,G,H>
 * Ennead<A,B,C,D,E,F,G,H,I> 
 * Decade<A,B,C,D,E,F,G,H,I,J> 
 * KeyValue<A,B> and
 * LabelValue<A,B>, which provide functionalities similar to Pair<A,B>, but
 * differ in semantics.
 */

//socket
