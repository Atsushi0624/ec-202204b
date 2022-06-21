package com.example.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginCustomer;

/**
 * ログイン情報を操作するコントローラ.
 * 
 * @author nao.yamada
 *
 */
@Controller
@RequestMapping("")
public class LoginController {
	@Autowired
	private HttpSession session;

	/**
	 * 直前のページのURLをセッションに保持し、ログインページを表示します.
	 * 
	 * @return ログインページ
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request) {
		String preUrl = request.getHeader("REFERER");
		session.setAttribute("preUrl", preUrl);
		return "login";
	}
	
	/**
	 * ログイン後の共通処理。ログインページを表示する前のURLによってれダイレクト先を分けます。ログインした顧客をセッションに保持します.
	 * 
	 * @param customer 顧客情報
	 * @return 商品一覧or注文確認
	 */
	@RequestMapping("/afterLogin")
	public String afterLogin(@AuthenticationPrincipal LoginCustomer customer) {
		
		// TODO OrderテーブルのuserIdの更新処理を追加する
		String preUrl = (String)session.getAttribute("preUrl");
		session.setAttribute("customer", customer.getCustomer());
		if(preUrl == null) {
			return "redirect:/show";
		}
		List<String> preUrlPathList = Arrays.asList(preUrl.split("/"));
		if (preUrlPathList.contains("order_confirm")) {
			session.removeAttribute("preUrl");
			return "redirect:/confirm_order";
		}else {
			return "redirect:/show";
		}
	}
	
	/**
	 * ログアウト処理の前にセッションに保存している顧客情報を削除します.
	 * 
	 * @return ログアウト処理
	 */
	@RequestMapping("/toLogout")
	public String beforeLogout() {
		session.removeAttribute("customer");
		return "redirect:/logout";
	}
}
