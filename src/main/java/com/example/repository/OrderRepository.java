package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author atsushi.kikuchi
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new ArrayList<>();

		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;

		int preOrderId = 0;
		int preOrderItemId = 0;
		while (rs.next()) {
			int orderId = rs.getInt("o_id");
			int orderItemId = rs.getInt("oi_id");

			if (orderId != preOrderId) {
				Order order = new Order();
				order.setId(rs.getInt("o_id"));
				order.setCustomerId(rs.getInt("user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setdeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));

				orderItemList = new ArrayList<OrderItem>();
				order.setOrderItemList(orderItemList);

				orderList.add(order);
			}

			if (orderId != 0) {
				if (orderId != preOrderId || orderItemId != preOrderItemId) {
					OrderItem orderItem = new OrderItem();
					orderItem.setId(rs.getInt("oi_id"));
					orderItem.setItemId(rs.getInt("item_id"));
					orderItem.setQuantity(rs.getInt("quantity"));
					orderItem.setSize(rs.getString("size"));

					Item item = new Item();
					item.setName(rs.getString("i_name"));
					item.setDescription(rs.getString("description"));
					item.setPriceM(rs.getInt("i_price_m"));
					item.setPriceL(rs.getInt("i_price_l"));
					item.setImagePath(rs.getString("image_path"));
					orderItem.setItem(item);

					orderToppingList = new ArrayList<OrderTopping>();
					orderItem.setOrderToppingList(orderToppingList);

					orderItemList.add(orderItem);
				}

				if (orderItemId != 0) {
					OrderTopping orderTopping = new OrderTopping();
					orderTopping.setId(rs.getInt("ot_id"));
					orderTopping.setToppingId(rs.getInt("topping_id"));
					orderTopping.setOrderItemId(rs.getInt("order_item_id"));

					Topping topping = new Topping();
					topping.setName(rs.getString("t_name"));
					topping.setPriceM(rs.getInt("t_price_m"));
					topping.setPriceL(rs.getInt("t_price_l"));

					orderTopping.setTopping(topping);

					orderToppingList.add(orderTopping);
				}
			}

			preOrderId = orderId;
			preOrderItemId = orderItemId;
		}

		return orderList;
	};

	/**
	 * 注文情報を挿入し、採番された番号を返す.
	 * 
	 * @param order 注文情報
	 * @return オーダーID
	 */
	public Integer insert(Order order) {
		String sql = "INSERT INTO orders "
				+ "(user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method) VALUES "
				+ "(:customerId, :status, :totalPrice, :orderDate, :destinationName, :destinationEmail, :destinationZipcode, :destinationAddress, :destinationTel, :deliveryTime, :paymentMethod);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(order);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };

		template.update(sql, param, keyHolder, keyColumnNames);

		return keyHolder.getKey().intValue();
	}

	/**
	 * オーダーIDから注文情報を取得.
	 * 
	 * @param orderId オーダーID
	 * @return 注文情報
	 */
	public Order load(Integer orderId) {
		String sql = "SELECT o.id as o_id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method, oi.id AS oi_id, item_id, quantity, size, i.name AS i_name, description, i.price_m AS i_price_m, i.price_l AS i_price_l, image_path, ot.id AS ot_id, topping_id, order_item_id, t.name AS t_name, t.price_m AS t_price_m, t.price_l AS t_price_l "
				+ "FROM orders AS o "
				+ "LEFT JOIN order_items AS oi "
				+ "ON o.id = oi.order_id "
				+ "LEFT JOIN items AS i "
				+ "ON oi.item_id = i.id "
				+ "LEFT JOIN order_toppings AS ot "
				+ "ON oi.id = ot.order_item_id "
				+ "LEFT JOIN toppings AS t "
				+ "ON ot.topping_id = t.id "
				+ "WHERE o.id = :orderId "
				+ "ORDER BY o.id, oi.id, ot.id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);

		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		System.out.println(orderList);
		return orderList.get(0);
	}

	/**
	 * ステータスと顧客IDからオーダーIDを取得.
	 * 
	 * @param status     注文状態
	 * @param customerId 顧客ID
	 * @return オーダーID
	 */
	public Integer findOrderIdByStatusAndUserId(Integer status, Integer customerId) {

		try {
			String sql = "SELECT id FROM orders "
					+ "WHERE status = :status AND user_id = :customerId;";

			SqlParameterSource param = new MapSqlParameterSource().addValue("status", status).addValue("customerId",
					customerId);

			Integer orderId = template.queryForObject(sql, param, Integer.class);

			return orderId;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/**
	 * ダミーIDを正規のIDに書き換える.
	 * 
	 * @param dummyCustomerId ダミー顧客ID
	 * @param customerId      正規の顧客ID
	 */
	public void updateUserId(Integer dummyCustomerId, Integer customerId) {
		String sql = "UPDATE orders SET user_id = :customerId "
				+ "WHERE user_id = :dummyCustomerId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("customerId", customerId)
				.addValue("dummyCustomerId", dummyCustomerId);

		template.update(sql, param);

	}

	/**
	 * 注文情報を更新.
	 * 
	 * @param order 注文情報
	 */
	public void update(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String sql = "UPDATE orders SET user_id=:customerId, status=:status, total_price=:totalPrice, order_date=:orderDate, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode, destination_address=:destinationAddress, destination_tel=:destinationTel, delivery_time=:deliveryTime, payment_method=:paymentMethod WHERE id = :id;";
		template.update(sql, param);
	}
}
