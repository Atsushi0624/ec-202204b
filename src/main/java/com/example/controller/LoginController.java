package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン情報を操作するコントローラ.
 * 
 * @author nao.yamada
 *
 */
@Controller
@RequestMapping("/toLogin")
public class LoginController {

	/**
	 * ログインページを表示します.
	 * 
	 * @return ログインページ
	 */
	@RequestMapping("")
	public String toLogin() {
		// デバッグ用（ログインユーザーの確認）
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();// get logged in username
		System.out.println("userName : " + name);
		// ここまで
		return "login";
	}
}
