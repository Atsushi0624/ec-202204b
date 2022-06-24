package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.AnalizeData;
import com.example.domain.Customer;
import com.example.domain.Item;

/**
 * おすすめ商品特定用の分析データを操作するリポジトリ.
 * 
 * @author nao.yamada
 *
 */
@Repository
public class AnalizeRepository {

	private  int itemNum;

	public  void setItemNum(int argItemNum) {
		itemNum = argItemNum;
	}

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 分析データオブジェクトを生成するローマッパー.
	 */
	private final RowMapper<AnalizeData> ANALIZE_DATA_ROW_MAPPER = (rs, i) -> {
		AnalizeData analizeData = new AnalizeData();
		Customer customer = new Customer();
		customer.setId(rs.getInt("id"));
		customer.setName(rs.getString("name"));
		customer.setEmail(rs.getString("email"));
		customer.setPassword(rs.getString("password"));
		customer.setZipcode(rs.getString("zipcode"));
		customer.setAddress(rs.getString("address"));
		customer.setTelephone(rs.getString("telephone"));
		analizeData.setCustomer(customer);
		List<Item> itemList = new ArrayList<>();
		for (int j=1; j<=itemNum; j++) {
			Item item = new Item();
			String columnName = "item" + String.valueOf(j);
			item.setAverageRate(rs.getDouble(columnName));
			item.setId(j);
			itemList.add(item);
		}
		analizeData.setItemList(itemList);
		return analizeData;
		
	};
	
	
	/**
	 * 分析用データを取得します.
	 * 
	 * @return
	 */
	public List<AnalizeData> getdata(){
		StringBuilder sql = new StringBuilder();
		sql.append("select u.id,u.name,u.email,u.password,u.zipcode,u.address,u.telephone,");
		for (int i=1; i<=itemNum; i++) {
			sql.append("(case when( avg(case when oi.item_id=");
			sql.append(String.valueOf(i));
			sql.append(" then oi.rate end) is null ) then 2.5 else avg(case when oi.item_id=");
			sql.append(String.valueOf(i));
			sql.append(" then oi.rate end) end) as item");
			sql.append(String.valueOf(i));
			sql.append(",");
		}
		// 末尾から1文字分を削除
		sql.setLength(sql.length()-1);
		
		sql.append(" from users as u left outer join orders as o ON u.id=o.user_id");
		sql.append(" left outer join order_items as oi ON o.id=oi.order_id");
		sql.append(" group by u.id;");
		List<AnalizeData> analizeDataList = template.query(sql.toString(), ANALIZE_DATA_ROW_MAPPER);
		return analizeDataList;
		
	}
}
