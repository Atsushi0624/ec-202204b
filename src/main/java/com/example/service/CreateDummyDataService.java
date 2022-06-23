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
		for(int i = 1; i <= dummyUserNum; i++) {
			Customer customer = new Customer();
			customer.setName("ダミーユーザー" + i);
			customer.setAddress("ダミー" );
			customer.setEmail("dummy" + i + "@sample.co.jp");
			customer.setZipcode("000-0000" );
			customer.setPassword("dummy");
			customer.setTelephone("000-0000-0000");
			int customerId = customerRepository.insert(customer);
			
			Order order = new Order();
			order.setCustomerId(customerId);
			order.setStatus(0);
			order.setTotalPrice(0);
			int orderId = orderRepository.insert(order);
			
			// 注文商品情報を登録
			Random random = new Random();
			for(int j = 1; j <= maxDummyOrderItemNum; j ++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(orderId);
				orderItem.setItemId(random.nextInt(MAX_ITEM_ID) + 1);
				orderItem.setQuantity(random.nextInt(maxQuantity) + 1);
				orderItem.setOrderId(random.nextInt(6));
				int size = random.nextInt(2);
				if(size == 0) {
					orderItem.setSize("M");					
				}else {
					orderItem.setSize("L");	
				}
				orderItemRepository.insert(orderItem);
			}
		}
	}
}
