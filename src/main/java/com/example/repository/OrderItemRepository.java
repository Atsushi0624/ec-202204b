package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 注文商品情報を挿入し、採番されたIDを返す.
	 * 
	 * @param orderItem 注文商品情報
	 * @return オーダーアイテムID
	 */
	public Integer insert(OrderItem orderItem) {
		String sql = "INSERT INTO order_items "
				+ "(item_id, order_id, quantity, size) VALUES "
				+ " (:itemId, :orderId, :quantity, :size);";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = {"id"};
		
		template.update(sql, param, keyHolder, keyColumnNames);
		
		return keyHolder.getKey().intValue();
	}
	
	/**
	 * IDを指定してレコードを削除.
	 * 
	 * @param id ID
	 */
	public void deleteById(Integer id) {
		String sql = "DELETE FROM order_items WHERE id = :id;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		template.update(sql, param);
	}
}
