package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ダミーデータを生成する.
	 * 
	 * @param dummyNum             生成するダミーユーザー数
	 * @param maxDummyOrderItemNum 生成するOrderItem数の最大値（１～最大値でランダムに生成する）
	 * @param maxQuantity          生成する数量の最大値
	 */
	public void createDummyData(int dummyUserNum, int maxDummyOrderItemNum, int maxQuantity) {
		Random random = new Random();
		int start = customerRepository.findMaxDummyCutomerId() + 1;
		for (int i = start; i < start + dummyUserNum; i++) {
			Customer customer = new Customer();
			customer.setName("ダミーユーザー" + i);
			customer.setAddress(String.valueOf(i));
			customer.setEmail("dummy" + i + "@sample.co.jp");
			customer.setZipcode("000-0000");
			customer.setPassword(passwordEncoder.encode("dummy"));
			customer.setTelephone("000-0000-0000");
			int gender = random.nextInt(2);
			if (gender == 0) {
				customer.setGender("男性");
			} else {
				customer.setGender("女性");
			}
			int age = random.nextInt(6);
			if (age == 0) {
				customer.setAge("10代");
			} else if (age == 1) {
				customer.setAge("20代");
			} else if (age == 2) {
				customer.setAge("30代");
			} else if (age == 3) {
				customer.setAge("40代");
			} else if (age == 4) {
				customer.setAge("50代");
			} else {
				customer.setAge("60代");
			}
			int customerId = customerRepository.insert(customer);

			Order order = new Order();
			order.setCustomerId(customerId);
			order.setStatus(0);
			order.setTotalPrice(0);
			int orderId = orderRepository.insert(order);

			// 注文商品情報を登録
			for (int j = 1; j <= maxDummyOrderItemNum; j++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setOrderId(orderId);
				orderItem.setItemId(random.nextInt(MAX_ITEM_ID) + 1);
				orderItem.setQuantity(random.nextInt(maxQuantity) + 1);
				int size = random.nextInt(2);
				if (size == 0) {
					orderItem.setSize("M");
				} else {
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
		customer.setZipcode("000-0000");
		customer.setPassword("dummy");
		customer.setTelephone("000-0000-0000");
		int gender = random.nextInt(2);
		if (gender == 0) {
			customer.setGender("男性");
		} else {
			customer.setGender("女性");
		}
		int age = random.nextInt(6);
		if (age == 0) {
			customer.setAge("10代");
		} else if (age == 1) {
			customer.setAge("20代");
		} else if (age == 2) {
			customer.setAge("30代");
		} else if (age == 3) {
			customer.setAge("40代");
		} else if (age == 4) {
			customer.setAge("50代");
		} else {
			customer.setAge("60代");
		}
		int customerId = customerRepository.insert(customer);

		return customerId;
	}

	/**
	 * ダミーオーダーの生成.
	 * 
	 * @param customerId 顧客ID
	 * @param itemId     商品ID
	 * @param rate       評価
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
		if (size == 0) {
			orderItem.setSize("M");
		} else {
			orderItem.setSize("L");
		}
		int orderItemId = orderItemRepository.insert(orderItem);
		orderItemRepository.updateRate(rate, orderItemId);
	}
	
	public void createDummyOrdersDependsOnCustomer(int customerId, int orderNumParCustomer) {
		for (int i=0; i <= orderNumParCustomer; i++) {
			Order order = new Order();
			order.setCustomerId(customerId);
			order.setStatus(0);
			order.setTotalPrice(0);
			int orderId = orderRepository.insert(order);
			
			OrderItem orderItem = new OrderItem();
			orderItem.setOrderId(orderId);
			List<Integer> orderItemIdAndRateList = getOrderItemIdDependsOnCustomer(customerId);
			int itemId = orderItemIdAndRateList.get(0);
			int rate = orderItemIdAndRateList.get(1);
			orderItem.setItemId(itemId);
			orderItem.setQuantity(1);
			orderItem.setSize("M");
			int orderItemId = orderItemRepository.insert(orderItem);
			orderItemRepository.updateRate(rate, orderItemId);
		}
	}

	public List<Integer> getOrderItemIdDependsOnCustomer(int customerId) {
		// itemIDのリストを作成
		List<Integer> itemIdList = new ArrayList<>();
		for (int i = 1; i <= MAX_ITEM_ID; i++) {
			itemIdList.add(i);
		}
		// itemIDのリストをcustomerIdによって並び替える
		Collections.rotate(itemIdList, customerId);

		// itemIDのリストの先頭ほど購入されずらいように、temIDに購入されやすさを制御する割合を対応させる
		Map<Integer, Double> itemSelectRatiotMap = new HashMap<>();
		int weightTotal = itemIdList.stream().mapToInt(Integer::valueOf).sum();
		double weight = 1.0; // itemIdに対応するweightは先頭から [1, 2, 3, ..., 18]
		for (int itemId : itemIdList) {
			itemSelectRatiotMap.put(itemId, weight / weightTotal);
			weight += 1.0;
		}

		// itemIDのリストを購入されやすい順に並べる
		Collections.reverse(itemIdList);

		// 購入する商品を確率的に選択する
		Random random = new Random();
		double rand = random.nextDouble();
		int orderItemId = 0;
		double ratioTotal = 0;
		for (int itemId : itemIdList) {
			ratioTotal += itemSelectRatiotMap.get(itemId);
			if (rand <= ratioTotal) {
				orderItemId = itemId;
				// 選択された商品が選択されやすいものであったほど評価を高くする
				int priority = itemIdList.indexOf(orderItemId);
				int rate;
				if(priority < 3) {
					rate = 5;
				}else if(priority < 7) {
					rate = 4;
				}else if(priority < 11) {
					rate = 3;
				}else if(priority < 15) {
					rate = 2;
				}else{
					rate = 1;
				}
				List<Integer> orderItemIdAndRateList = Arrays.asList(orderItemId, rate);
				return orderItemIdAndRateList;
			}
		}
		return null;
	}
}
