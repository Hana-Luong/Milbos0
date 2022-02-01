package com.milbos0.main.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milbos0.main.models.Post;
import com.milbos0.main.models.User;
import com.milbos0.main.repos.UserRepo;


@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;					//Is it NOT a singleton
//	private final UserRepo userRepo;    
//    public UserService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
    
    public User registerUser(User user) {		
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);				
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepo.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();		//get u?
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
	
	public List<User> allUsers(){
		return userRepo.findAll();
	}

	public User save(User u) {
		return userRepo.save(u);
	}
	
	public User findById(Long id) {
		Optional<User> user = userRepo.findById(id);
		
		if(user.isPresent()) {
			return user.get();
		}else {
			return null;
		}
	}
	
	// postId comes from URL <a href="localhost/posts/like/3"></a>
	// user.likePost(postId) 
	
	//REVIEW THIS. 
	public void likePost(Long userId, Post post) {
		Optional<User> user = userRepo.findById(userId);
				
		if (user.get() != null) {
			user.get().setLikedPost(post);		
			userRepo.save(user.get());
		}
	}
}

/*
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
 * categoryRepository.save(thisCategory);
 * 
 * 
 * //This has the same affect as above:
 * thisProduct.getCategories().add(thisCategory); // add the category to this
 * products's list of categories productRepository.save(thisProduct); // Save
 * thisProduct, since you made changes to its category list.
 * 
 * 
 */