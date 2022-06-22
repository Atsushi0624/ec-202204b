package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.service.OrderHistoryService;

@Controller
@RequestMapping("")
public class OrderHistoryController {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderHistoryService orderHistoryService;
	
	@RequestMapping("/showOrderHistory")
	public String showOrderHistory() {
		Customer customer = (Customer) session.getAttribute("customer");
		List<Order> orderList = orderHistoryService.getOrderHistory(customer.getId());
		System.out.println(orderList);
		return "order_history";
	}
}
