package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Topping;

/**
 * itemsテーブルを操作するリポジトリ
 * 
 * @author ryosuke.moritani
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	ToppingRepository toppingRepository;

	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setImagePath(rs.getString("image_path"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setDeleted(rs.getBoolean("deleted"));
		item.setAverageRate(rs.getDouble("rate"));
		ArrayList<Topping> toppingList = new ArrayList<>();
		item.setToppingList(toppingList);
		return item;
	};

	/**
	 * 商品を全件取得する.
	 * 
	 * @return 商品リスト
	 */
	public List<Item> findAll() {
		String sql = "SELECT i.id, name, description,  image_path, price_m, price_l, deleted, "
				+ "avg(oi.rate) as rate "
				+ "FROM items as i left outer join order_items as oi ON oi.item_id=i.id " + "GROUP BY i.id "
				+ "ORDER BY price_m";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品を検索する.
	 * 
	 * @param itemName 商品名
	 * @return 検索された商品一覧
	 */
	public List<Item> findByName(String itemName) {
		String sql = "SELECT i.id, name, description,  image_path, price_m, price_l, deleted, "
				+ "avg(oi.rate) as rate "
				+ "FROM items as i left outer join order_items as oi ON oi.item_id=i.id " + "GROUP BY i.id "
				+ "WHERE name ILIKE :name ORDER BY price_m";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + itemName + "%");
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 主キーから商品情報を取得する.
	 * 
	 * @param id 商品id
	 * @return 商品情報
	 */
	public Item load(Integer id) {
		String sql = "SELECT i.id, name, description,  image_path, price_m, price_l, deleted, "
				+ "avg(oi.rate) as rate "
				+ "FROM items as i left outer join order_items as oi ON oi.item_id = i.id "
				+ "WHERE i.id=:id "
				+ "group by i.id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	/**
	 * 商品を売り上げ個数順で取得します.
	 * 
	 * @return
	 */
	public List<Item> findAllSortedBySales() {
		String sql = "SELECT i.id, i.name, i.description, i.image_path, i.price_m, i.price_l, i.deleted,"
				+ "sum((case when oi.quantity is null then 0 else oi.quantity end)) as sales, "
				+ "avg(oi.rate) as rate "
				+ "FROM items as i left outer join order_items as oi ON oi.item_id=i.id " + "GROUP BY i.id "
				+ "ORDER BY sales desc;";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 商品を評価順で取得します.
	 * 
	 * @return
	 */
	public List<Item> findAllSortedByRate() {
		String sql = "SELECT " + "i.id, i.name, i.description, i.image_path, i.price_m, i.price_l, i.deleted,"
				+ "avg(oi.rate) as rate "
				+ "FROM items as i left outer join order_items as oi ON oi.item_id = i.id " + "group by i.id "
				+ "ORDER BY rate desc NULLS last";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
}
