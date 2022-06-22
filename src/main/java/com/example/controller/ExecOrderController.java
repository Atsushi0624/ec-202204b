package com.example.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.form.ExecOrderForm;
import com.example.service.ExecOrderService;

@Controller
@RequestMapping("")
public class ExecOrderController {

	@Autowired
	private HttpSession session;

	@Autowired
	ExecOrderService execOrderService;

	@ModelAttribute
	public ExecOrderForm setUpExecOrderForm() {
		return new ExecOrderForm();
	}

	@RequestMapping("/exec_order")
	public String execOrder(ExecOrderForm form) throws ParseException {
		Order order = execOrderService.getOrder(form.getOrderId());
		BeanUtils.copyProperties(form, order);

		Customer customer = (Customer) session.getAttribute("customer");
		order.setCustomerId(customer.getId());

		// 注文ステータスの更新
		if (form.getPaymentMethod() == 1) {
			order.setStatus(1);
		} else if (form.getPaymentMethod() == 2) {
			order.setStatus(2);
		}
		
		// 配達日時のセット
		String strDeliveryTime = form.getDeliveryTimeList().get(0) + " " + form.getDeliveryTimeList().get(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh");
		Date deliveryTime = sdf.parse(strDeliveryTime);
		Timestamp DeliveryTimeStamp = new Timestamp(deliveryTime.getTime());
		order.setdeliveryTime(DeliveryTimeStamp);
		
		// 注文日に現在の日時を入れる
		order.setOrderDate(new Date());
		System.out.println(order);
		execOrderService.execOrder(order);
		return "order_finished";
	}
}
