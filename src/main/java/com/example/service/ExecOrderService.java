package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class ExecOrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private MailSender sender;

  public void execOrder(Order order) {
    orderRepository.update(order);
  }

  public Order getOrder(Integer orderId) {
    return orderRepository.load(orderId);
  }

  public void sendMail() {
    SimpleMailMessage msg = new SimpleMailMessage();

    msg.setFrom("liangjiesengu743@gmail.com");
    msg.setTo("sessyomaru427@gmail.com");
    msg.setSubject("テストメール");// タイトルの設定
    msg.setText("Spring Boot より本文送信"); // 本文の設定

    sender.send(msg);
  }
}
