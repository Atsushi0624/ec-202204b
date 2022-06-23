package com.example.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

@Service
public class ExecOrderService {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private JavaMailSender sender;

  public void execOrder(Order order) {
    MimeMessage message = sender.createMimeMessage();
    try {
      // 送信情報設定
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom("ryosu123456@gmail.com");
      helper.setTo(order.getDestinationEmail());
      helper.setSubject("注文が完了しました");
      String insertMessage = "<html>"
          + "<head></head>"
          + "<body>"
          + "<h3>注文完了</h3>"
          + "<p>" + order.getDestinationName() + "</p>"
          + "</body>"
          + "</html>";
      helper.setText("本文", insertMessage);
      // メール送信
      sender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    orderRepository.update(order);
  }

  public Order getOrder(Integer orderId) {
    return orderRepository.load(orderId);
  }
}
