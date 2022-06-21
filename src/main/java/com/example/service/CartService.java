package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * カートを操作するサービス.
 * 
 * @author atsushi.kikuchi
 *
 */
@Service
public class CartService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	/**
	 * 顧客IDから注文が完了していないオーダーIDを取得する.
	 * 存在しなかれば新規作成し、採番されたオーダーIDを返す
	 * 
	 * @param customerId 顧客ID
	 * @return オーダーID
	 */
	public Integer getOrCreateOrderId(Integer customerId) {
		Integer orderId = orderRepository.findOrderIdByStatusAndUserId(0, customerId);
		// オーダーIDが見つからなかった場合は新しく作成する
		if(orderId == null) {
			Order order = new Order();
			order.setCustomerId(customerId);
			order.setStatus(0);
			order.setTotalPrice(0);
			System.out.println("test");
			orderId = orderRepository.insert(order);
		}
		
		return orderId;
	}
	
	/**
	 * オーダーIDから注文情報を取得. 
	 *
	 * @param orderId オーダーID
	 * @return　注文情報
	 */
	public Order getOrder(Integer orderId) {
		return orderRepository.load(orderId);
	}
	
	/**
	 * 注文商品情報をDBに登録し、採番されたIDを返す.
	 * 
	 * @param orderItem 注文商品情報
	 * @return 採番されたorderItemId
	 */
	public int createOrderItem(OrderItem orderItem) {
		return orderItemRepository.insert(orderItem); 
	}
	
	/**
	 * 注文トッピング情報をDBに登録し、採番されたIDを返す.
	 * 
	 * @param orderTopping 注文トッピング情報
	 * @return 採番されたorderToppingId
	 */
	public void createOrderTopping(OrderTopping orderTopping) {
		orderToppingRepository.insert(orderTopping); 
	}
	
	/**
	 * IDを指定して注文商品情報を削除.
	 * その注文商品に紐づくトッピング情報も削除される
	 * 
	 * @param orderItemId 注文商品ID
	 */
	public void removeOrderItem(int orderItemId) {
		orderItemRepository.deleteById(orderItemId);
	}
	
	/**
	 * 注文情報を更新.
	 * 
	 * @param order
	 */
	public void update(Order order) {
		orderRepository.update(order);
	}
}
