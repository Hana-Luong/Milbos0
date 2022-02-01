package com.milbos0.main.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="users")
public class User {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	
	@Size(min=2)
    private String name;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Size(min=5, message="Cannot be blank")
	@Email(message="Email must be valid")
    private String email;
	
	@Size(min=8, message="Cannot be blank")
    private String password;
    
    @Transient
    private String passwordConfirmation;
    
    /* EXAMPLE 
     * mappedBy="person" represents the field that owns the relationship; "person" in the other table;
     * cascade=CascadeType.ALl make sure referential integrity is preserved in ALL actions
	 * 
	 * @OneToOne(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	 * private License license;
	 * 
	 */
    
	/* EXAMPLE 
	 * @OneToMany(mappedBy="dojo", fetch = FetchType.LAZY)
     * private List<Ninja> ninjas;
	 */
    
	@OneToMany(mappedBy="creator", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Post> createdPosts; 	
	//field "creator" is in the other table. "creator" owns "posts".    
	
	
	/*
	 * EXAMPLE WITH A MIDDLE TABLE
	 *
	 	@Table(name="categories_products") 
	 	@ManyToOne(fetch = FetchType.LAZY)
    	@JoinColumn(name="product_id")
    	private Product product;
    ---------------------------------------------------------------------------------	
     	@ManyToOne(fetch = FetchType.LAZY)
    	@JoinColumn(name="category_id")
    	private Category category;
    ----------------------------------------------------------------------------------
    	@JsonIgnore: We are using this annotation to solve an infinite recursion issue
    	with Json and JPA. Therefore, we ignore that attribute when it's being 
    	serialized into json.

		@JoinTable: Defines the middle table the our entities will be mapped to.
		@JoinTable(name="categories_products"): The name of the middle table.
		joinColumns: The foreign key that matches the primary key of the embedded class 
		when the tables are joined.
		inverseJoinColumns: The foreign key that matched the foreign key of the opposite
		 class when the tables are joined.
	 */
		
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable( name="lPostsLikers",
				joinColumns = @JoinColumn(name="liker_id"),
				inverseJoinColumns = @JoinColumn(name="likedPost_id"))
	@JsonIgnore
	private List<Post> likedPosts;  
	
	/*
	 * From your Service, adding a relationship between two existing records: 
	 * - Fetch both the specific product and category out of the database,
	 * -  and assigning the returned object instances to variables. 
	 * - Add the product to the products list for that category.
	 * - Remember, products is a member variable in the Category class. 
	 * - Save the category object.
	 * 
	 * // retrieve an instance of a category using another method in the service.
	 * Category thisCategory = findCategoryById(categoryId);
	 * 
	 * // retrieve an instance of a product using another method in the service.
	 * Product thisProduct = findProductById(productId);
	 * 
	 * // add the product to this category's list of products
	 * thisCategory.getProducts().add(thisProduct);
	 * 
	 * // Save thisCategory, since you made changes to its product list.
	 * categoryRepository.save(thisCategory); You can also add the category to that
	 * product's category list, and it does the same thing.
	 * 
	 * // This has the same affect as above:
	 * thisProduct.getCategories().add(thisCategory); // add the category to this
	 * 		products's list of categories 
	 * productRepository.save(thisProduct); // SavethisProduct, since you made changes to its category list. 
	 * 
	 * In other words, you can implement the relationship from either side, and 
	 * Spring JPA will run the SQL in the database to assign foreign keys in the 
	 * join table for you.
	 */	
	
	
	
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;    
   
    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
    
    public User() {
    }
    
    public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}


	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public List<Post> getCreatedPosts() {
		return createdPosts;
	}
	public void setCreatedPosts(List<Post> createdPosts) {
		this.createdPosts = createdPosts;
	} 
	
	//getter and setter of likedPosts
	public List<Post> getLikedPosts() {
		return likedPosts;
	}
	public void setLikedPost(Post likedPost) {
		this.likedPosts.add(likedPost);
	}
      
}