package com.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginCustomer;
import com.example.service.LoginService;

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
	
	@Autowired
	private LoginService loginService;

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
		session.setAttribute("customer", customer.getCustomer());
		if(customer.getUsername().equals("admin@admin.com")){
			session.setAttribute("isAdmin", true);
		}
		
		
		
		// dummyCustomerIdがセットされていればcustomerIDの更新を行う
		Integer dummyCustomerId = (Integer)session.getAttribute("dummyCustomerId");
		if(dummyCustomerId != null) {
			int customerId = customer.getCustomer().getId();
			session.removeAttribute("dummyCustomerId");
			loginService.updateCustomerId(dummyCustomerId, customerId);
		}
		
		// 「注文画面へ」ボタンによってログイン画面に遷移していた場合は注文確認画面へ飛ばす
		Integer toOrderConfirmFlag = (Integer)session.getAttribute("toOrderConfirm");
		if(toOrderConfirmFlag != null) {
			session.removeAttribute("toOrderConfirm");
			return "redirect:/confirmOrder";			
		}else {
			return "redirect:/";
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
		session.setAttribute("idAdmin", false);
		return "redirect:/logout";
	}
	
	
}
