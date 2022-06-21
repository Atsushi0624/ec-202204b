package com.example.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.CartForm;
import com.example.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	
	/** ダミーIDの上限 */
	
	final int MAX_DUMMY_ID = 1000000000;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/add")
	public String addOrderItem(CartForm form) {
		// TODO: 顧客IDをSpringSecurityから取ってくるように変更
		Integer customerId = null;
		if(customerId == null) {
			customerId = getDummyCustomerId();
		}
		
		int orderId = cartService.getOrCreateOrderId(customerId);
		
		// 注文商品情報を登録
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);
		orderItem.setItemId(form.getItemId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		int orderItemId = cartService.createOrderItem(orderItem);
		
		// トッピング情報を登録
		List<Integer> toppingIdList = form.getToppingIdList();
		for(Integer toppingId: toppingIdList) {
			OrderTopping orderTopping = new OrderTopping();
			orderTopping.setOrderItemId(orderItemId);
			orderTopping.setToppingId(toppingId);
			cartService.createOrderTopping(orderTopping);
		}
			
		return "redirect:/cart/showCart";
	}

	
	@RequestMapping("/showCart")
	public String showCartItem(Model model) {
		// TODO: 顧客IDをSpringSecurityから取ってくるように変更
		Integer customerId = null;
		
		if(customerId == null) {
			customerId = getDummyCustomerId();
			return "cart_list";
		}

		int orderId = cartService.getOrCreateOrderId(customerId);
		Order order = cartService.getOrder(orderId);
		model.addAttribute("order", order);
		
		return "cart_list";
	}
	
	/**
	 * セッションスコープからダミーIDを取得.
	 * 取得できない場合は新しく生成
	 * 
	 * @return ダミーID
	 */
	private Integer getDummyCustomerId() {
		Integer customerId = (Integer) session.getAttribute("dummyCustomerId");
		
		// それでも取れなかった場合はダミーを生成
		if(customerId == null) {
			Random random = new Random();
			int dummyCustomerId = random.nextInt(MAX_DUMMY_ID) - MAX_DUMMY_ID;
			session.setAttribute("dummyCustomerId", dummyCustomerId);
			customerId = dummyCustomerId;
		}
		return customerId;
	}
	
}
