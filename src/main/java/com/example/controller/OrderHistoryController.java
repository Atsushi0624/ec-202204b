package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.service.OrderHistoryService;

/**
 * 注文履歴を表示させるコントローラ.
 * 
 * @author atsushi.kikuchi
 *
 */
@Controller
@RequestMapping("")
public class OrderHistoryController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderHistoryService orderHistoryService;
	
	/**
	 * 注文履歴を表示.
	 * 
	 * @param model
	 * @return 注文履歴画面
	 */
	@RequestMapping("/showOrderHistory")
	public String showOrderHistory(Model model) {
		Customer customer = (Customer) session.getAttribute("customer");
		List<Order> orderList = orderHistoryService.getOrderHistory(customer.getId());
		if(orderList.size() != 0) {
			model.addAttribute("orderList", orderList);
		}
		return "order_history";
	}
	
	/**
	 * 商品を評価.
	 * 
	 * @return 注文履歴画面
	 */
	@RequestMapping("/evaluateItem")
	public String evaluateItem(Integer rate, Integer orderItemId) {
		if(rate == null) {
			rate = 0;
		}
		orderHistoryService.updateRate(rate, orderItemId);
		return "redirect:/showOrderHistory";
	}
}
