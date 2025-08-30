package com.example.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class UserLoginController {
	
	private final UserService userService;
	
	@GetMapping("/user-login")
	/*valueはURLの?error=xxxを受け取る。requiredのtrueの場合はerrorパラメーターがないと400エラーになるので
	  falseにしてerrorパラメーターがない場合はnullを受け取るようにする*/
	public String loginForm(@RequestParam(value="error", required=false) String error,
							Model model) {
		
		if(error != null) {
			
			model.addAttribute("loginError", true);
		}
		
		return "login/userlogin";
	}
	
	@GetMapping("/mypage")
	/*PrincipalはjavaのインタフェースでSpringSecurityと連動しておりログイン中のユーザー情報を取り出す為に使用する
	  メソッドはString getName()のみ*/
	public String mypage(Model model, Principal principal) {
		
		/*principalのgetName()でログイン中の情報を取り出して
		  findByUserEmailでDB内にユーザーが存在しているか確認する*/
		UserForm userForm = userService.findByUserEmail(principal.getName());
		
		//viewに渡す
		model.addAttribute("userForm", userForm);
		
		return "login/mypage";
	}
	
}
