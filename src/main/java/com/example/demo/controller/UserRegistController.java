package com.example.demo.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserRegistController {
	
	private final UserService userService;
	
	@GetMapping("/user-regist")
	public String newRegistForm(@ModelAttribute UserForm userForm) {
		
		return "regist/user";
	}
	
	@PostMapping("/user-complete")
	public String registUser(@Valid @ModelAttribute UserForm userForm,
								 BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			
			return "regist/user";
		}
		
		if(userService.existsByEmail(userForm.getUserEmail())) {
			
			/*BindingResultが持つメソッドでバリデーションエラーを特定のフィールドに紐づける
			  1番目がエラー対象のフィールド、2番目がエラーコード、3番目がエラーメッセージ*/
			bindingResult.rejectValue("userEmail", "duplicate", "このメールアドレスは既に登録されいます");
			return "regist/user";
		}
		
		userService.save(userForm);
		
		/*二重登録防止のためPRGパターンを使用する。
		  redirectの後のreregist/completeこれがGETとして飛ばされる*/
		return "redirect:/regist-complete";
	}
	
	@GetMapping("/regist-complete") 
	public String registComplete() {
		
		return "regist/complete";
	}

}