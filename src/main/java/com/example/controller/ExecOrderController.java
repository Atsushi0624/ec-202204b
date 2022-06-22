package com.example.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
	
	@Autowired
	private ConfirmOrderController confirmOrderController;

	@ModelAttribute
	public ExecOrderForm setUpExecOrderForm() {
		return new ExecOrderForm();
	}

	@RequestMapping("/exec_order")
	public String execOrder(@Validated ExecOrderForm form, BindingResult result, Model model) throws ParseException {
		// 配達日時のチェック
		String strDeliveryTime = form.getDeliveryTimeList().get(0) + " " + form.getDeliveryTimeList().get(1);
		LocalDateTime deliveryTime = LocalDateTime.parse(strDeliveryTime, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		System.out.println(strDeliveryTime);
		System.out.println(deliveryTime);
		System.out.println(deliveryTime.minusHours(3));
		System.out.println(deliveryTime);
		deliveryTime.minusHours(3);
		if(LocalDateTime.now().isBefore(deliveryTime.minusHours(3))) {
			FieldError fieldError = new FieldError(result.getObjectName(), "deliveryTimeList", "今から3時間以後の日時をご入力ください");
			result.addError(fieldError);
		}
		if (result.hasErrors()) {
			return confirmOrderController.toOrderConfirm(1, model);
		}

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
		Timestamp DeliveryTimeStamp = Timestamp.valueOf(deliveryTime);
		order.setdeliveryTime(DeliveryTimeStamp);

		// 注文日に現在の日時を入れる
		order.setOrderDate(new Date());
		System.out.println(order);
		execOrderService.execOrder(order);
		return "order_finished";
	}
}
