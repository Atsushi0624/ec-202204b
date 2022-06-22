package com.example.controller;

import java.sql.Timestamp;
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

/**
 * 注文をそ操作するコントローラ.
 * 
 * @author nao.yamada
 *
 */
@Controller
@RequestMapping("")
public class ExecOrderController {

	@Autowired
	private HttpSession session;

	@Autowired
	ExecOrderService execOrderService;

	@Autowired
	private ConfirmOrderController confirmOrderController;

	/**
	 * 注文フォームの用意.
	 * 
	 * @return 注文フォーム
	 */
	@ModelAttribute
	public ExecOrderForm setUpExecOrderForm() {
		return new ExecOrderForm();
	}

	/**
	 * 注文を実行します.
	 * 
	 * @param form   注文フォーム
	 * @param result 入力値チェック用のBindingResult
	 * @param model  注文確認画面に戻るメソッドの引数に使用
	 * @return 注文完了画面
	 */
	@RequestMapping("/exec_order")
	public String execOrder(@Validated ExecOrderForm form, BindingResult result, Model model) {
		LocalDateTime deliveryTime = null;
		if (form.getDeliveryTimeList().size() == 2) { // form.getDeliveryTimeList()は日にちと時間がListで入っているので両方入っているか確認する
			// 配達日時のチェック
			String strDeliveryTime = form.getDeliveryTimeList().get(0) + " " + form.getDeliveryTimeList().get(1) + ":00:00";
			deliveryTime = LocalDateTime.parse(strDeliveryTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			if (LocalDateTime.now().isBefore(deliveryTime.minusHours(3))) {
				FieldError fieldError = new FieldError(result.getObjectName(), "deliveryTimeList", "今から3時間以後の日時をご入力ください");
				result.addError(fieldError);
			}
		} else {
			FieldError fieldError = new FieldError(result.getObjectName(), "deliveryTimeList", "配達日時を選択してください");
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
		execOrderService.execOrder(order);
		execOrderService.sendMail();
		return "order_finished";
	}
}
