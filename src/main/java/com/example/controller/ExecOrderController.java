package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.ExecOrderForm;
import com.example.service.ExecOrderService;

@Controller
@RequestMapping("")
public class ExecOrderController {
  @Autowired
  ExecOrderService execOrderService;

  @ModelAttribute
  public ExecOrderForm setUpExecOrderForm() {
    return new ExecOrderForm();
  }

  @RequestMapping("/exec_order")
  public String execOrder(ExecOrderForm form) {
    Order order = execOrderService.getOrder(form.getId());
    order.setCustomerId(form.getCustomerId());
    if (form.getStatus() == 1) {
      order.setStatus(1);
    } else if (form.getStatus() == 2) {
      order.setStatus(2);
    }
    order.setTotalPrice(form.getTotalPrice());
    order.setOrderDate(form.getOrderDate());
    order.setDestinationName(form.getDestinationName());
    order.setDestinationEmail(form.getDestinationEmail());
    order.setDestinationZipcode(form.getDestinationZipcode());
    order.setDestinationAddress(form.getDestinationAddress());
    order.setDestinationTel(form.getDestinationTel());
    order.setdeliveryTime(form.getDeliveryTime());
    order.setPaymentMethod(form.getPaymentMethod());
    execOrderService.execOrder(order);
    return "order_finish";
  }
}
