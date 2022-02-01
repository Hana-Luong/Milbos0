package com.milbos0.main.controllers;

import javax.servlet.http.HttpSession;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.milbos0.main.models.User;
import com.milbos0.main.services.UserService;
import com.milbos0.main.validators.UserValidator;

@Controller
public class UsersController {
    private final UserService userService;		//why private final?
    private UserValidator userValidator;		//why NOt final?

	/*
	 * final is a non-access modifier for Java elements. The final modifier is used
	 * for finalizing the implementations of classes, methods, and variables. A
	 * final instance variable can be explicitly initialized only once. (At time of
	 * declaration; In constructor; instance block;)
	 */
    
    public UsersController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    
    @RequestMapping("/loginreg")
    public String showLoginRegisterForms(@ModelAttribute("user") User user) {
        return "regAndLogin.jsp";
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String submitRegisterForm(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValidator.validate(user, result);
    	
    	if(result.hasErrors()) {
    		return "regAndLogin.jsp";
    	}
    //Find out where registerUser(User user) lives. 
    //Relearn setAttribute( , );
    	
    	User newUser = userService.registerUser(user);
    	session.setAttribute("userid", newUser.getId());
  		return "redirect:/allposts";
    }
    
    //Explaine @RequestParam
    //Model model 
    //@ModelAttribute
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String submitLoginForm(@RequestParam("email") String email, 
    						@RequestParam("password") String password, 
    						Model model, 
    						HttpSession session,
    						@ModelAttribute("user") User user) {
    	
    	if(userService.authenticateUser(email, password)) {
      		User thisUser = userService.findByEmail(email);
    		session.setAttribute("userid", thisUser.getId());
    		return "redirect:/allposts";
    	}
        	model.addAttribute("error", "Invalid login");
    	return "regAndLogin.jsp";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
    	System.out.println("Logged out userid " + session.getAttribute("userid"));
    	session.invalidate();
        // invalidate session
        // redirect to login page
    	return "redirect:/allposts";
    }
}
