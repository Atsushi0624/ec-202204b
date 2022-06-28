package com.example.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderRepository;

/**
 * 注文する機能のサービス.
 * 
 * @author ryosuke.moritani
 *
 */
@Service
public class ExecOrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private JavaMailSender sender;
	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * 注文する.
	 * 
	 * @param order 注文情報
	 */
	@Async
	public void execOrder(Order order) {
		MimeMessage message = sender.createMimeMessage();
		try {
			// 送信情報設定
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom("ryosu123456@gmail.com");
			helper.setTo(order.getDestinationEmail());
			helper.setSubject("注文が完了いたしました【ラクラクcoffee】");
			order.getOrderItemList();
			String orderedItem = "";
			for (OrderItem orderItem : order.getOrderItemList()) {
				orderedItem += "<tr><td>" + orderItem.getItem().getName() + "</td><td>"
						+ String.valueOf(orderItem.getSize()) + "</td>" + "<td>"
						+ String.valueOf(orderItem.getQuantity()) + "</td></tr>";
			}
			String insertMessage = "<html>" + "<head></head>" + "<body>" + "<p>" + order.getDestinationName() + "様</p>"
					+ "<p>ラクラクcoffeeをご利用いただきありがとうございます。以下の内容でご注文を承りました。</p>" + "<hr>"
					+ "<tr><table border='1em'><tr><th>品目</th><th>サイズ</th><th>数量</th></tr>" + orderedItem + "</table>"
					+ "<p>ご請求金額：　　￥" + String.format("%,d", order.getCalcTotalPrice() + order.getTax()) + "</p>"
					+ "<hr>" + "<a href='http://localhost:8080/ec-202204b/showOrderHistory'>注文履歴</a>" + "</body>"
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
