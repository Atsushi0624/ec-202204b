package com.example.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
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

	private static final RowMapper<Map<Item, Integer>> RANKED_ITEM_ROW_MAPPER = (rs, i) -> {
		Map<Item, Integer> rankedItemSalesMap = new HashMap<>();
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setImagePath(rs.getString("image_path"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setDeleted(rs.getBoolean("deleted"));
		rankedItemSalesMap.put(item, rs.getInt("sales"));
		return rankedItemSalesMap;
	};

	/**
	 * 注文商品情報を挿入し、採番されたIDを返す.
	 * 
	 * @param orderItem 注文商品情報
	 * @return オーダーアイテムID
	 */
	public Integer insert(OrderItem orderItem) {
		String sql = "INSERT INTO order_items " + "(item_id, order_id, quantity, size) VALUES "
				+ " (:itemId, :orderId, :quantity, :size);";

		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumnNames = { "id" };

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

	/**
	 * orderIDを更新. 注. 新しいオーダーIDを古いオーダーIDに合わせる ログイン時に既に未注文の商品があった場合に使用
	 * 
	 * @param oldOrderId 古いオーダーID
	 * @param newOrderId 新しいオーダーID
	 */
	public void updateCustomerId(Integer oldOrderId, Integer newOrderId) {
		String sql = "UPDATE order_items SET order_id = :oldOrderId " + "WHERE order_id = :newOrderId;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("oldOrderId", oldOrderId).addValue("newOrderId",
				newOrderId);

		template.update(sql, param);
	}

	/**
	 * 指定IDの評価を更新.
	 * 
	 * @param rate 評価
	 * @param id   ID
	 */
	public void updateRate(Integer rate, Integer id) {
		String sql = "UPDATE order_items SET rate = :rate " + "WHERE id = :id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("rate", rate).addValue("id", id);

		template.update(sql, param);
	}

	public List<Map<Item, Integer>> rankedItemListByAgeAndGender(String age, String gender) {
		String sql = "select i.id as id, i.name as name, i.description as description, i.image_path image_path, i.price_m price_m, i.price_l price_l, i.deleted deleted sales "
				+ "from items as i " + " left outer join " + "(SELECT oi.item_id, sum(quantity) as sales "
				+ " FROM order_items as oi " + " left outer join orders as o ON o.id = oi.order_id "
				+ " left outer join users as u ON o.user_id = u.id " + " where u.age=:age AND u.gender=:gender "
				+ " group by oi.item_id) as sub on i.id = sub.item_id " + "order by sales desc nulls last;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("age", age).addValue("gender", gender);
		List<Map<Item, Integer>> rankedItemList = template.query(sql, param, RANKED_ITEM_ROW_MAPPER);
		return rankedItemList;
	}
}
