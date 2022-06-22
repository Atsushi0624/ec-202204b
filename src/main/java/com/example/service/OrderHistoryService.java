package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Order;
import com.example.repository.OrderRepository;

/**
 * 注文履歴を表示する機能のサービス.
 * 
 * @author atsushi.kikuchi
 *
 */
@Service
public class OrderHistoryService {

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 顧客IDを指定して注文情報一覧を取得.
	 * 
	 * @param custromerId 顧客ID
	 * @return 注文情報のリスト
	 */
	public List<Order> getOrderHistory(Integer custromerId) {
		return orderRepository.findByCustomerId(custromerId);
	}
}
