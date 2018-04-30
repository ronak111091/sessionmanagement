package sw.arch.sessionmanagement.controller;

import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sw.arch.sessionmanagement.model.Role;
import sw.arch.sessionmanagement.model.User;
import sw.arch.sessionmanagement.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/app/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		StringJoiner sj = new StringJoiner(",");
		for(Role role: user.getRoles()) {
			sj.add(role.getRole());
		}
		modelAndView.addObject("role","["+sj.toString()+"]");
		modelAndView.addObject("adminMessage","Welcome to the home page of the application");
		modelAndView.setViewName("app/home");
		return modelAndView;
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/app/manageuser", method = RequestMethod.GET)
	public ModelAndView manageUser() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getAuthorities());
		List<User> users = userService.getAllUsers();
		modelAndView.addObject("message","Content Available Only for Users with Admin Role");
		modelAndView.addObject("users",users);
		modelAndView.setViewName("app/manage-user");
		return modelAndView;
	}
	
	@RequestMapping(value={"/accessDenied"}, method = RequestMethod.GET)
	public ModelAndView accessDenied(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("access-denied");
		return modelAndView;
	}
	
	@RequestMapping(value={"/invalidSession"}, method = RequestMethod.GET)
	public ModelAndView invalidSession(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sessionExpired");
		return modelAndView;
	}
	
	@RequestMapping(value={"/sessionExpired"}, method = RequestMethod.GET)
	public ModelAndView sessionExpired(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("sessionExpired");
		return modelAndView;
	}
	
	@RequestMapping(value={"/app/aboutus"}, method = RequestMethod.GET)
	public ModelAndView aboutUs(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("app/aboutUs");
		return modelAndView;
	}
}
