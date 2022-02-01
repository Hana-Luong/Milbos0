package com.milbos0.main.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.milbos0.main.models.Post;
import com.milbos0.main.models.User;
import com.milbos0.main.services.PostService;
import com.milbos0.main.services.UserService;

//possibly used as API

@Controller
public class PostController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	//DISPLAY ALL THE POSTS AVAILABLE
	
	@GetMapping("/allposts")
	public String showAll(Model model) {
		List<Post> posts = postService.allPosts();
		model.addAttribute("allPosts", posts);
		return "postsAll.jsp";			
	}
	
	/*
	 * @RequestMapping("/books") 
	 * public String index(Model model) { 
	 * 		List<Book> books = bookService.allBooks(); 
	 * 		model.addAttribute("books", books); 
	 * 		return "/books/index.jsp";
	 */	
	
	
	//(1) DISPLAY A FORM FOR USER TO (2)ENTER INPUT AND SUBMIT
	@GetMapping("/allposts/new")
	public String showCreatePostForm(@ModelAttribute("post") Post post, Model model, HttpSession session) {
		if(session.getAttribute("userid") != null) {
			model.addAttribute("user", userService.allUsers());
			return "postCreate.jsp";			
		}
		else
			return "redirect:/loginreg";
	}
	
	@PostMapping("/allposts/new")
	public String submitPost(@Valid @ModelAttribute("post") Post post, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "postCreate.jsp";	
		}			
		post.setCreator((User)userService.findById((Long)session.getAttribute("userid")));
		postService.save(post);
		return "redirect:/allposts";	
	}
	
	
//	 @RequestMapping("/books/new")
//	    public String newBook(@ModelAttribute("book") Book book) {
//	        return "/books/new.jsp";
//	    }
	
	/*
	 * a new use of @ModelAttribute: not accessing data sent with the request, but
	 * rather instantiating a new Book type and binding to our view model. Since
	 * there is no Book being passed, an empty book will be instantiated and bound
	 * to our view model. The binding to our model will allow us to create a form
	 * that is also bound to the Book type, which will allow us to validate the
	 * information against our Book type!
	 */
	
//	@RequestMapping(value="/books", method=RequestMethod.POST)
//  public String create(@Valid @ModelAttribute("book") Book book, BindingResult result) {
//      if (result.hasErrors()) {
//      	return "/books/new.jsp";
//      } else {
//      	bookService.createBook(book);
//          return "redirect:/books";
//        }
//    }

	/*
	 * Error Checking In the post request to create a book, we use the @Valid
	 * annotation to check to see if the submitted object passes validation. Once
	 * this is done, we can also inject the BindingResult to see if the object
	 * passed validation. This must come immediately after the @Valid parameter.
	 * Once we have the BindingResult we can check if there were any errors, and
	 * then reload our form with errors if there are any.
	 * 
	 */	
	
	
	// DISPLAY A SINGLE POST IN THE LIST
	
	@GetMapping("/allposts/{post_idPlaceHolder}")
	public String showOnePost(@PathVariable("post_idPlaceHolder") Long id, Model model) {
			Post post = postService.findById(id);
			model.addAttribute("post", post);
			return "postSingle.jsp";			
	}
	
	//(1)DISPLAY A FORM FOR EDITING AND (2)LET THE USER SUBMIT THE EDITED INFO
	
    @GetMapping("/allposts/{post_id_to_be_edited}/edit")
	public String showEditForm(@PathVariable("post_id_to_be_edited") Long id, Model model, HttpSession session) {
    	Long session_id = (Long) session.getAttribute("userid"); //CAST
    	Post post = postService.findById(id);
    	Long creator_id = post.getCreator().getId();			//FIND CREATOR

    	if(creator_id.equals(session_id)) {    		
    		model.addAttribute("post", post);
    		model.addAttribute("logged_user_id", session.getAttribute("userid")); 

    		return "postEdit.jsp";
    	}
		else
			return "redirect:/allposts";
	}
    
    //@RequestMapping(value="/books/{id}", method=RequestMethod.PUT)
	@PostMapping("/allposts/{post_id_has_been_edited}/edit")
	public String submitEdit(@PathVariable("post_id_has_been_edited") Long id, 
			@Valid @ModelAttribute("post") Post post, BindingResult result, 
			HttpSession session) {
		if(result.hasErrors()) {
			return "postEdit.jsp";	
		}			
		
		postService.save(post);
		return "redirect:/allposts";	
	}
		
	
//DELETE
	
    @RequestMapping(value="/allposts/{idPH}", method=RequestMethod.DELETE)
    public String destroy(@PathVariable("idPH") Long id) {
    	postService.delete(id);				//Oh, two functions in the service file.
        return "redirect:/allposts";
    }   
    
    
    
    
	
    @RequestMapping("/allposts/{liked_post_id}/like")
    public String likeAPost(@PathVariable("liked_post_id") Long postId,
    		HttpSession session) {
    	Long userId = (Long) session.getAttribute("userid");

    	if (userId != null) {
    		Post post = postService.findById(postId);
    		userService.likePost(userId, post);//many to many association
    		
    		/*User user = userService.findById(userId);
    		for (Post p : user.getLikedPosts())
    			System.out.println("Liked post ids: " + p.getId());*/
    		
    		return "redirect:/allposts";
    	} else {
			return "redirect:/loginreg";
    	}
    }
}
/*
 * ROUTING:  Every request will be organized based on two parts: the path and the
 * request method. The request method: GET by default.public PostController()
 * URL = https://en.wikipedia.org/wiki/URL 
 * Path = /wiki/URL
 */
/*
 * One primary way to transmit information to the server is by the URL parameters. 
 * We will be using the @PathVariable annotation.
 */

/*
 * View Model
Now that we can render a jsp file, resolve it to html that will display nicely 
for the user, it's time to focus on getting data into the jsp from our controller. 
We pass data to our view using a Model object, Model model, which implements 
the map interface and exposes the key-value pairs in our view. 
To pass data to our view =  injecting a Model object to our controller method:
key = ${key}, webpage will display the corresponding value. 
*/
/*
 * Persistent Data Storage
Persistent data storage can come in many forms =  a database +  writing to a file. 
session = writing to a file;

a HTTP request/response is stateless, 
but in the scope of a given req/res cycle, we can read certain pieces of data that 
we stored in previous cycles, and write certain valuable pieces of data for use 
in future cycles. 
a user makes decisions that can be tracked so that a server can respond
appropriately to create a better user experience.

HttpSession session
 */


/*
 * Dependency Injection 
 * Model model This is
 * a powerful design pattern that inverts the usual control flow of a program
 * (where your class would instantiate the classes that it needs). This process
 * is called "Inversion of Control," or "IoC". Together IoC and DI are a large
 * part of modern Java frameworks.
 * 
 * The way it works is a container (the IoC container) finds all the
 * dependencies, instantiates them, and injects them where required. An example
 * that you've used is the Model model in your controller methods:
 * 
 * You never instantiate model anywhere, but somehow it is there for us to use -
 * that is because of the IoC Container. This process decouples the class
 * construction from the construction of its dependencies, which results in
 * cleaner, more modular, and more maintainable code. It also makes testing,
 * when you get there, much easier.
 * 
 * https://docs.spring.io/spring-framework/docs/current/reference/html/core.html
 * #beans
 */

/*
 * APIs vs. Full-Stack fully functioning back-end application with CRUD :send
 * requests to create, read, update and delete records using solely the url
 * endpoints, with Postman, = an Application Programming Interface (API).
 * 
 * @RestController annotation: what gets sent back in the HTTP Response?
 * responding with raw data, strings or JSON; = API
 * 
 * @Controller: Build a full-stack application, we need to render .jsp files,
 * Requests will now come from user interaction
 * 
 * Before you continue, make sure that you have installed the following
 * dependencies: tomcat-embed-jasper and jstl.
 */
	