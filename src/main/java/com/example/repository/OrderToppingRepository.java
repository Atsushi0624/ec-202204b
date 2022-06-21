package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * order_toppingsテーブルを操作するリポジトリ.
 * 
 * @author atsushi.kikuchi
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 注文トッピング情報を挿入し、採番されたIDを返す.
	 * 
	 * @param orderItem 注文商品情報
	 * @return オーダートッピングID
	 */
	public Integer insert(OrderTopping orderTopping) {
		String sql = "INSERT INTO order_toppings "
				+ "(topping_id, order_item_id) VALUES "
				+ " (:toppingId, :orderItemId);";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = {"id"};
		
		template.update(sql, param, keyHolder, keyColumnNames);
		
		return keyHolder.getKey().intValue();
	}
	
	
}
