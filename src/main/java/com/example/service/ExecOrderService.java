package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class ExecOrderService {
  @Autowired
  private OrderRepository orderRepository;

  public String execOrder(Order order) {
    return orderRepository.update(order);
  }

  public String getOrder(Integer orderId) {
    return orderRepository.load(orderId);
  }
}
