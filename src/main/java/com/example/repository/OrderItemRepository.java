package com.example.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

/**
 * order_itemsテーブルを操作するリポジトリ.
 * 
 * @author atsushi.kikuchi
 *
 */
@Repository
public class OrderItemRepository {
	
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderItem.class);
	
	/**
	 * 注文商品情報を挿入し、採番されたIDを返す.
	 * 
	 * @param orderItem 注文商品情報
	 * @return オーダーアイテムID
	 */
	public Integer insert(OrderItem orderItem) {
		return null;
	}
	
	/**
	 * IDを指定してレコードを削除.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
	}
}
