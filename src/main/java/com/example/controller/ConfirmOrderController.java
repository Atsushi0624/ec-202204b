package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.ExecOrderForm;
import com.example.service.CartService;

@Controller
@RequestMapping("")
public class ConfirmOrderController {

	@Autowired
	private CartService cartService;

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
	public String toOrderConfirm(String orderId, Model model) {
		Order order = cartService.getOrder(Integer.parseInt(orderId));
		model.addAttribute("orderItemList", order.getOrderItemList());
		model.addAttribute("order", order);
		System.out.println(order);
		return "order_confirm";
	}

}
