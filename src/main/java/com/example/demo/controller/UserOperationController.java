package com.example.demo.controller;

import java.security.Principal;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserOperationController {
	
	private final UserServiceImpl userService;
	
	@GetMapping("/user-info-update")
	public String userUpdate(Model model, Principal principal) {
		
		UserForm userForm = userService.findByUserEmail(principal.getName());
		model.addAttribute("userForm", userForm);
		
		return "operation/update";
	}
	
	@PostMapping("/user-update-complete")
	public String updateComplete(@Validated @ModelAttribute UserForm userForm,
								 BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "operation/update";
		}
		
		try {
			
			userService.checkEmailAndUserUpdate(userForm);
		} catch(IllegalArgumentException e) {
			
			bindingResult.rejectValue("userEmail", "duplicate", e.getMessage());
			return "operation/update";
		}
		
		
		/*二重登録防止のためPRGパターンを使用する。
		  redirectの後の/operation/update-completeこれがGETとして飛ばされる*/
		return "redirect:/operation/update-complete";
		
	}
	
	@GetMapping("/operation/update-complete")
	public String redirectComplete() {
		
		return "operation/update-complete";
	}
	
	@GetMapping("/user-delete")
	public String deleteOperation(Model model,Principal principal) {
		
		UserForm userForm = userService.findByUserEmail(principal.getName());
		model.addAttribute("userForm", userForm);
		
		return "operation/delete";
	}
	
	@PostMapping("/delete-check")
	public String finalCheck() {
		
		return "operation/final-delete-check";
	}
	
	@PostMapping("/delete-ok")
	//requestはサーバーにあるセッション情報、responseはブラウザにあるCookie情報
	public String deleteUser(HttpServletRequest request, HttpServletResponse response, Principal principal) {
		
		//ユーザー削除
		userService.deleteUser(principal.getName());
		
		//セッション破棄
		//3番目の引数はAuthenticationでnullを渡すことで対象を今ログイン中のユーザーにする
		new SecurityContextLogoutHandler().logout(request,response,null);
		
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String showTop() {
		
		return "home/top";
	}
}
