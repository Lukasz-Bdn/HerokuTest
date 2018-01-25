package pl.schoolmanager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.schoolmanager.bean.SessionManager;
import pl.schoolmanager.entity.SchoolUser;
import pl.schoolmanager.entity.UserRole;
import pl.schoolmanager.repository.UserRepository;
import pl.schoolmanager.repository.UserRoleRepository;
import pl.schoolmanager.validator.EditUsernameValidator;
import pl.schoolmanager.validator.NewUsernameValidator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserRoleRepository userRoleRepo;

	// CREATE
	@GetMapping("/create")
	public String createUser(Model m) {
		m.addAttribute("user", new SchoolUser());
		return "user/new_user";
	}

	@PostMapping("/create")
	public String createUserPost(@Validated(NewUsernameValidator.class) SchoolUser schoolUser, BindingResult bindingResult, 
								Model m) {
		if (bindingResult.hasErrors()) {
			return "redirect:/user/new_user";
		} else {
			UserRole userRole = new UserRole();
			schoolUser.setEnabled(true);
			userRole.setUsername(schoolUser.getUsername());
			userRole.setSchoolUser(schoolUser);
			userRole.setUserRole("ROLE_USER");
			this.userRepo.save(schoolUser);
			this.userRoleRepo.save(userRole);
			return "redirect:/user/all";
		}
	}
	
	// READ
	@GetMapping("/view/{userId}")
	public String viewUser(Model m, @PathVariable long userId) {
		SchoolUser schoolUser = this.userRepo.findOne(userId);
		m.addAttribute("user", schoolUser);
		return "user/show_user";
	}

	// UPDATE
	@GetMapping("/update/{userId}")
	public String updateUserRole(Model m, @PathVariable long userId) {
		SchoolUser schoolUser = this.userRepo.findOne(userId);
		m.addAttribute("user", schoolUser);
		HttpSession session = SessionManager.session();
		session.setAttribute("password", schoolUser.getPassword());
		return "user/edit_user";
	}

	@PostMapping("/update/{id}")
	@Transactional
	public String updateUserPost(@Validated(EditUsernameValidator.class) @ModelAttribute SchoolUser schoolUser, 
			BindingResult bindingResult, @PathVariable long id) {
		if (bindingResult.hasErrors()) {
			return "user/edit_user";
		}
		HttpSession session = SessionManager.session();
		schoolUser.setPasswordEncrypted(session.getAttribute("password").toString());
		session.setAttribute("password", null);
		this.userRepo.save(schoolUser);
		this.userRoleRepo.updateWithUsernameByUserId(id, schoolUser.getUsername());
		return "redirect:/user/all";
	}
	
	//CHANGE USER PASSWORD
	@GetMapping("/changepassword/{userId}")
	public String changePassword(@PathVariable long userId, Model m) {
		SchoolUser schoolUser = userRepo.findOne(userId);
		m.addAttribute("user", schoolUser);
		return "user/change_pass";
	}
	
	@PostMapping("/changepassword/{userId}")
	public String changePasswordPost(@RequestParam("password1") String password1, Model m,
									@RequestParam("password2") String password2, @PathVariable long userId) {
		SchoolUser schoolUser = userRepo.findOne(userId);
		if (!password1.equals(password2)) {
			m.addAttribute("msg", "Both passwords must match!");
			m.addAttribute("user", schoolUser);
			return "user/change_pass";
		}
		schoolUser.setPassword(password1);
		userRepo.save(schoolUser);
		return "redirect:/user/all";
	}

	// DELETE
	@GetMapping("/delete/{userId}")
	public String deleteUser(@PathVariable long userId, Model m) {
		SchoolUser schoolUser = this.userRepo.findOne(userId);
		m.addAttribute("user", schoolUser);
		return "user/confirm_delete";
	}
	
	@PostMapping("/delete/{userId}")
	public String deleteUserPost(@PathVariable long userId, Model m) {
		this.userRepo.delete(userId);
		return "redirect:/user/all";
		
	}

	// SHOW ALL
	@GetMapping("/all")
	public String allUsers(Model m) {
		return "user/all_users";
	}
	
	//Model Attributes
	@ModelAttribute("availableUsers")
	public List<SchoolUser> getUsers() {
		return this.userRepo.findAll();
	}
	
}
