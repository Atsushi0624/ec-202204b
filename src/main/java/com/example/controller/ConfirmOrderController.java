package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.form.ExecOrderForm;
import com.example.service.CartService;

@Controller
@RequestMapping("")
public class ConfirmOrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 注文用フォームを用意します.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public ExecOrderForm setUpExecOrderForm() {
		return new ExecOrderForm();
	}

	@RequestMapping("/confirmOrder")
	public String toOrderConfirm(Integer toOrderConfirm, Model model) {
		// ログインされていない状態でははじく
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer == null) {
			session.setAttribute("toOrderConfirm", 1);
			return "login";
		}
		
		Integer orderId = cartService.getOrCreateOrderId(customer.getId());
		
		Order order = cartService.getOrder(orderId);
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("order", order);
		return "order_confirm";
	}

}
