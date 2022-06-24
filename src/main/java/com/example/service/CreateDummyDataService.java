package com.example.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;

@Service
@Transactional
public class CreateDummyDataService {

	/** アイテムIDの上限値 */
	private final int MAX_ITEM_ID = 18;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * ダミーデータを生成する.
	 * 
	 * @param dummyNum 生成するダミーユーザー数
	 * @param maxDummyOrderItemNum 生成するOrderItem数の最大値（１～最大値でランダムに生成する）
	 * @param maxQuantity 生成する数量の最大値
	 */
	public void createDummyData(int dummyUserNum, int maxDummyOrderItemNum, int maxQuantity) {
		Random random = new Random();
		int start = customerRepository.findMaxDummyCutomerId() + 1;
		for(int i = start; i < start + dummyUserNum; i++) {
			Customer customer = new Customer();
			customer.setName("ダミーユーザー" + i);
			customer.setAddress(String.valueOf(i));
			customer.setEmail("dummy" + i + "@sample.co.jp");
			customer.setZipcode("000-0000" );
			customer.setPassword("dummy");
			customer.setTelephone("000-0000-0000");
			int gender = random.nextInt(2);
			if(gender == 0) {
				customer.setGender("男性");					
			}else {
				customer.setGender("女性");	
			}
			int age = random.nextInt(6);
			if(age == 0) {
				customer.setAge("10代");		
			}else if(age == 1){
				customer.setAge("20代");	
			}else if(age == 2){
				customer.setAge("30代");	
			}else if(age == 3){
				customer.setAge("40代");	
			}else if(age == 4){
				customer.setAge("50代");	
			}else{
				customer.setAge("60代");	
			}
			int customerId = customerRepository.insert(customer);
			
			Order order = new Order();
			order.setCustomerId(customerId);
			order.setStatus(0);
			order.setTotalPrice(0);
			int orderId = orderRepository.insert(order);
			
			// 注文商品情報を登録
			for(int j = 1; j <= maxDummyOrderItemNum; j ++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(orderId);
				orderItem.setItemId(random.nextInt(MAX_ITEM_ID) + 1);
				orderItem.setQuantity(random.nextInt(maxQuantity) + 1);
				int size = random.nextInt(2);
				if(size == 0) {
					orderItem.setSize("M");					
				}else {
					orderItem.setSize("L");	
				}
				int orderItemId = orderItemRepository.insert(orderItem);
				orderItemRepository.updateRate(random.nextInt(6), orderItemId);
			}
		}
	}
	
	/**
	 * ダミーユーザーの生成.
	 * 
	 * @return 採番された顧客ID
	 */
	public int createDummyUser() {
		Random random = new Random();
		int dummyId = customerRepository.findMaxDummyCutomerId() + 1;
		Customer customer = new Customer();
		customer.setName("ダミーユーザー" + dummyId);
		customer.setAddress(String.valueOf(dummyId));
		customer.setEmail("dummy" + dummyId + "@sample.co.jp");
		customer.setZipcode("000-0000" );
		customer.setPassword("dummy");
		customer.setTelephone("000-0000-0000");
		int gender = random.nextInt(2);
		if(gender == 0) {
			customer.setGender("男性");					
		}else {
			customer.setGender("女性");	
		}
		int age = random.nextInt(6);
		if(age == 0) {
			customer.setAge("10代");		
		}else if(age == 1){
			customer.setAge("20代");	
		}else if(age == 2){
			customer.setAge("30代");	
		}else if(age == 3){
			customer.setAge("40代");	
		}else if(age == 4){
			customer.setAge("50代");	
		}else{
			customer.setAge("60代");	
		}
		int customerId = customerRepository.insert(customer);
		
		return customerId;
	}
	
	/**
	 * ダミーオーダーの生成.
	 * 
	 * @param customerId　顧客ID
	 * @param itemId 商品ID
	 * @param rate 評価
	 */
	public void createDummyOrder(int customerId, int itemId, int rate) {
		Order order = new Order();
		order.setCustomerId(customerId);
		order.setStatus(0);
		order.setTotalPrice(0);
		int orderId = orderRepository.insert(order);
		
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(orderId);
		orderItem.setItemId(itemId);
		Random random = new Random();
		orderItem.setQuantity(1);
		int size = random.nextInt(2);
		if(size == 0) {
			orderItem.setSize("M");					
		}else {
			orderItem.setSize("L");	
		}
		int orderItemId = orderItemRepository.insert(orderItem);
		orderItemRepository.updateRate(rate, orderItemId);
	}
}
