package pl.schoolmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/")
public class HomeController {

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

	@GetMapping("403")
	public String accessDenied() {
		return "errors/accessDenied";
	}

}