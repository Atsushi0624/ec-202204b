package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.ExecOrderForm;

@Controller
@RequestMapping("")
public class ExecOrderController {
  @Autowired
  ExecOrderService execOrderService;

  @RequestMapping("/exec_order")
  public String execOrder(ExecOrderForm form) {

    return "order_finish";
  }
}
