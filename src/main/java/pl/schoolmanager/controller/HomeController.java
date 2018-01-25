package pl.schoolmanager.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.schoolmanager.entity.SchoolUser;
import pl.schoolmanager.entity.UserRole;
import pl.schoolmanager.repository.UserRepository;
import pl.schoolmanager.repository.UserRoleRepository;

@Controller("/")
public class HomeController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserRoleRepository userRoleRepo;


	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;

	
	@GetMapping("/")
	public String home() {
		return "home/home";
	}

	@GetMapping("login")
	public String loginGet() {
		return "home/login";
	}

	@PostMapping("login")
	public String loginPost() {
		return "redirect:/";
	}
	
	@GetMapping("register")
	public String registerGet(Model m) {
		m.addAttribute("user", new SchoolUser());
		return "home/register";
	}
	
	@PostMapping("register")
	@Transactional
	public String registerPost(@Valid @ModelAttribute SchoolUser schoolUser, BindingResult bindingResult, Model m) {
		if (bindingResult.hasErrors()) {
			return "redirect:register";
		}
		if (!schoolUser.isPasswordCorrent(schoolUser.getConfirmPassword())) {
			m.addAttribute("msg", "Please make sure that both passwords match!");
			return "home/register";
		}
		UserRole userRole = new UserRole();
		schoolUser.setEnabled(true);
		userRole.setUsername(schoolUser.getUsername());
		userRole.setSchoolUser(schoolUser);
		userRole.setUserRole("ROLE_USER");
		this.userRepo.save(schoolUser);
		this.userRoleRepo.save(userRole);
		return "redirect:/login";
	}

	@GetMapping("403")
	public String accessDenied() {
		return "errors/accessDenied";
	}
	
	
	@RequestMapping("/db")
	String db(Model m) {
		try (Connection connection = dataSource.getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS names (name varchar(80))");
//			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
//			stmt.executeUpdate("INSERT INTO names VALUES ('Lukasz')");
			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

			ArrayList<String> output = new ArrayList<String>();
			while (rs.next()) {
				output.add("Read from DB: " + rs.getTimestamp("tick"));
				System.out.println(rs.getTimestamp("tick"));
			}

			ResultSet rs2 = stmt.executeQuery("SELECT name FROM names");

			ArrayList<String> output2 = new ArrayList<String>();
			while (rs2.next()) {
				output2.add("Read from DB: " + rs2.getString("name"));
				System.out.println(rs2.getString("name"));
			}

			m.addAttribute("ticks", output);
			m.addAttribute("names", output2);
			
			return "home/db";
		} catch (Exception e) {
			m.addAttribute("message", e.getMessage());
			System.out.println(e.getMessage());
			return "home/error";
		}
	}
	
}