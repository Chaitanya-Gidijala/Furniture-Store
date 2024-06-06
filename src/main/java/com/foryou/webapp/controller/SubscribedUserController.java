package com.foryou.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foryou.webapp.entity.SubscribedUser;
import com.foryou.webapp.service.EmailService;
import com.foryou.webapp.service.SubscribedUserService;

import jakarta.mail.MessagingException;


@Controller
public class SubscribedUserController {
	
    private final SubscribedUserService userService;
    private final EmailService emailService;

    public SubscribedUserController(SubscribedUserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }
    
	@GetMapping("/user-form")
	public String showMyLoginPage(Model theModel) {
		theModel.addAttribute("user", new SubscribedUser());
		return "subscribed-user";
	}
	
	@PostMapping("/save-user")
	public String saveUser(SubscribedUser user, @RequestParam("email") String email, Model model) throws MessagingException {
	    List<SubscribedUser> subscribedUsers = userService.getAllSubscribedUsers();
	    boolean emailExists = false;
	    for (SubscribedUser userFromDb : subscribedUsers) {
	        if (userFromDb.getEmail().equals(email)) {
	            emailExists = true;
	            break;
	        }
	    }

	    if (emailExists) {
	        System.out.println("Your already a registered user...");
	        emailService.sendEmail(email,"Your Alredy Register",
   				 "Dear User,\n\n" +
                    "Your already register with" + " 'ForYouJobs' portal" + ".\n" +
                    "Thank You for revisting...\n\n" +
                    "Best regards,\n" +
                    "Furniture Store"
                    );
   		return "redirect:/";
   		
	    } 
	    else {
	        System.out.println("You're a new user. Thank you for registering with us...");
	        userService.saveSubscribedUser(user);
	        
    		System.out.println("user saved into your Database...");
    		
    	    model.addAttribute("successMessage", "Your subscription saved successfully!");

    		emailService.sendEmail(user.getEmail(), "Registration Confirmation!", 
    				"Dear User,\n\n" +
                    "Thank you for registering with " + " 'ForYou Furniture Store' shopping portal" + ".\n" +
                    "Your registration was successful.\n\n" +
                    "Best regards,\n" +
                    "ForYou Furniture Store");
    		System.out.println("-------------- Mail Sent Successfully..-----------------");
	    }

	    return "redirect:/";
	}

    @GetMapping("/send-emails")
    public String sendEmails() {
        List<SubscribedUser> subscribedUsers = userService.getAllSubscribedUsers();
        for (SubscribedUser user : subscribedUsers) {
        	
        	
            emailService.sendEmail(user.getEmail(), "Registration Confirmation!", 
            				"Dear User,\n\n" +
                            "Thank you for registering with " + " 'ForYou Furniture Store' shopping portal" + ".\n" +
                            "Your registration was successful.\n\n" +
                            "Best regards,\n" +
                            "ForYou Furniture Store");
        }
        return "Emails sent successfully!";
    }
}
