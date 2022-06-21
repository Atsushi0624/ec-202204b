package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;

@Controller
@RequestMapping("")
public class ExecOrderController {
  @Autowired
  ExecOrderService execOrderService;

  @RequestMapping("/exec_order")
  public String execOrder(ExecOrderForm form) {
    Order order = execOrderService.getOrder(orderId);
    order.setDestinationName(form.);
    execOrderService.execOrder(order);
  }
}
