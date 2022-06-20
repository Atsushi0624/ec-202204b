package com.example.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = new BeanPropertyRowMapper<>(OrderTopping.class);
	
	/**
	 * 注文トッピング情報を挿入し、採番されたIDを返す.
	 * 
	 * @param orderItem 注文商品情報
	 * @return オーダートッピングID
	 */
	public Integer insert(OrderTopping orderTopping) {
		return null;
	}
	
	
}
