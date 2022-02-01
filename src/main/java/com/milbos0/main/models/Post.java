package com.milbos0.main.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*Domain Model
The domain model is simply a Java Bean that represents our "business data," 
or the information that we want about a particular thing. 
Since the Post class must be a Java Bean, it must have private attributes, getter and 
setters in the getField() and setField() formats, and an empty constructor.*/

/*Java beans objects be serialized so that the state of a bean class could be preserved 
in case required.So due to this a Java Bean class must either implements Serializable 
or Externalizable interface.
All JavaBeans are POJOs but not all POJOs are JavaBeans.*/

/*public class Employee implements java.io.Serializable 
Classes that are eligible for serialization need to implement a special marker 
	interface Serializable.
Note that static fields belong to a class (as opposed to an object), not serialized. 
Serialization = the process of translating a data structure or object state into a 
format that can be stored (for example, in a file or memory data buffer) 
or transmitted (for example, over a computer network) and reconstructed later 
(possibly in a different computer environment)*/

@Entity
@Table(name="posts") 					//Look at this table in mySQL
public class Post {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;					//This column in table "posts" and others 
	
	@Size(min = 2, max = 200)
	private String title;
	
	@Size(min = 2, max = 2000)
	private String content;
	
	/* EXAMPLE 
	 * 
	 * this side:
	 * @OneToOne(fetch=FetchType.LAZY)
     * @JoinColumn(name="person_id")
     * private Person person; 
     *  
     * @JoinColumn(name="person_id"): Defines mapping for composite foreign keys. 
     * It indicates that the corresponding table to this entity has a foreign_key 
     * to the referenced table.
	 */
	
	
	/*
	 * EXAMPLE	 
	 * @ManyToOne(fetch = FetchType.LAZY)	  
	 * @JoinColumn(name="dojo_id") //FOREIGN KEY IN THE OTHER TABLE
	 * private Dojo dojo;
	 */
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator_id")	//creator_id column in the posts table
	@JsonIgnore
	private User creator;			//No "creator" column? 	
	
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable( name="lPostsLikers",
				joinColumns = @JoinColumn(name="likedPost_id"),
				inverseJoinColumns = @JoinColumn(name="liker_id"))
	@JsonIgnore
	private List<User> liker;	
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
    public Post() {
    	
    }
    
    public Post(String content) {
    	this.content = content;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	} 
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<User> getLiker() {
		return liker;
	}
	public void setLiker(List<User> liker) {
		this.liker = liker;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
		
}

/*
 * We have used the word "model" in few different contexts:
 * course. (1) Model in the MVC design pattern for web applications. 
 * (2) Model object, which is available in Spring MVC's controllers. 
 * (3) New: Domain model, a java bean that contains annotation to represent 
 * the application's data. 
 * 
 * Both in Spring MVC and in the current definition of the MVC design pattern, 
 * the Model is responsible for managing the application's business logic and data. 
 * 
 * However,
 * in Spring MVC, the business logic is handled by the Service Layer (SL) and
 * the data is handled by the Persistence Layer (PL). The SL and PL together
 * make up for what is known as the M in MVC in today's web applications.
 * 
 * Persistence Layer: This layer is in charge of managing the application's
 * data. PL is made up of Domain models and Repositories. We will use
 * Repositories to access our database via an object relational mapper (ORM).
 * 
 * Service Layer: It is made up of classes that implement the business logic of
 * our application. It will call on the repositories to execute some sort of
 * transaction according to the request from the user.
 */


/*JPA
 * to persist data = to store data in a database. 
 * making use of the Java Persistence API (JPA). Spring Data JPA provides 
 * 		a familiar and consistent, Spring-based programming model for data access 
 * 		while still retaining the special traits of the underlying data store.
 * Spring Data JPA is built to use the JPA as the persistence driver. 
 * Of course since JPA is just a specification and not an implementation, 
 * we will need something to actually implement the Java Persistence API.
 * 
 * Dependencies and Set up:
 * We will be using MySQL as our datastore with JPA, 
 * so you only need to add two new dependencies for this:
 * - Spring Boot Spring Data JPA starter (JPA)
 * - Java Mysql Connector (MySQL)
 * The spring-boot-starter-data-jpa actually pulls in a few other dependencies, 
 * including Hibernate, so you only need to make sure that you have those two.
 */

/* application.properties file:
 
 * you need to have the schema created beforehand, and if you make mistakes in setting
 * up, you may need to drop your schema.
 * Appending timezone information on the end of your mysql url: * 
 * spring.datasource.url=jdbc:mysql://localhost:3306/book-schema?serverTimezone=
 * UTC
 * Depending on what version you are using you may see a deprecation
 * warning: Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver
 * class is `com.mysql.cj.jdbc.Driver'. 
 * 
 * In that case, remove this setting: * 
 * spring.datasource.driver-class-name=com.mysql.jdbc.Driver
 */