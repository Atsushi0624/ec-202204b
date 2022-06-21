package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン情報を操作するコントローラ.
 * 
 * @author nao.yamada
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * ログインページを表示します.
	 * 
	 * @return ログインページ
	 */
	@RequestMapping("toLogin")
	public String toLogin() {
		return "login";
	}
}
