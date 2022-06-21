package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.CartForm;
import com.example.service.CartService;

/**
 * カートを操作するコントローラ.
 * 
 * @author atsushi.kikuchi
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController { 
	
	/** ダミーIDの上限 */
	final int MAX_DUMMY_ID = 1000000000;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 商品を追加する.
	 * 
	 * @param form カートフォーム
	 * @return カートリスト画面
	 */
	@RequestMapping("/addItem")
	public String addOrderItem(CartForm form) {
		System.out.println(form);
		// TODO: 顧客IDをSpringSecurityから取ってくるように変更

		Integer customerId = null;
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer != null) {
			customerId = customer.getId();			
		}
		if(customerId == null) {
			customerId = getDummyCustomerId();
		}
		
		// test
		System.out.println(customerId);
		form.setItemId(1);
		form.setQuantity(2);
		form.setSize("M");
		List<Integer> list = new ArrayList<>();
		form.setToppingIdList(list);
		
		int orderId = cartService.getOrCreateOrderId(customerId);
		
		// totalPriceの更新
		Order order = cartService.getOrder(orderId);
		order.setTotalPrice(order.getCalcTotalPrice());
		cartService.update(order);
		
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

	
	/**
	 * カート内の商品を表示.
	 * 
	 * @param model
	 * @return カートリスト画面
	 */
	@RequestMapping("/showCart")
	public String showCartItem(Model model) {
		Integer customerId = null;
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer != null) {
			customerId = customer.getId();			
		}
		if(customerId == null) {
			customerId = getDummyCustomerId();
		}
		
		int orderId = cartService.getOrCreateOrderId(customerId);
		Order order = cartService.getOrder(orderId);
		
		// 商品リストがあるか判定
		if(order.getOrderItemList().get(0).getId() != 0) {			
			model.addAttribute("orderItemList", order.getOrderItemList());
			model.addAttribute("totalPrice", order.getTax() + order.getCalcTotalPrice());
			model.addAttribute("tax", order.getTax());
		}
		
		return "cart_list";
	}
	
	/**
	 * 商品を削除する.
	 * 
	 * @param orderItemId 注文商品ID
	 * @return カートリスト画面
	 */
	@RequestMapping("/removeItem")
	public String deleteItem(int orderItemId) {
		Integer customerId = null;
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer != null) {
			customerId = customer.getId();			
		}
		if(customerId == null) {
			customerId = getDummyCustomerId();
		}
		
		int orderId = cartService.getOrCreateOrderId(customerId);
		
		cartService.removeOrderItem(orderItemId);
		
		// totalPriceの更新
		Order order = cartService.getOrder(orderId);
		order.setTotalPrice(order.getCalcTotalPrice());
		cartService.update(order);
		
		return "redirect:/cart/showCart";
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
