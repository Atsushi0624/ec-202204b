package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class ExecOrderService {
  @Autowired
  private OrderRepository orderRepository;

  public void execOrder(Order order) {
    orderRepository.update(order);
  }

  public Order getOrder(Integer orderId) {
    return orderRepository.load(orderId);
  }
}
