package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

/**
 * Customer情報を操作するリポジトリ.
 * 
 * @author nao.yamada
 *
 */
@Repository
public class CustomerRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * Customerオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, i) -> {
		Customer customer = new Customer();
		customer.setId(rs.getInt("id"));
		customer.setName(rs.getString("name"));
		customer.setEmail(rs.getString("email"));
		customer.setPassword(rs.getString("password"));
		customer.setZipcode(rs.getString("zipcode"));
		customer.setAddress(rs.getString("address"));
		customer.setTelephone(rs.getString("telephone"));
		return customer;
	};
	
	/**
	 * Customer挿入する.
	 * 
	 * @param customer 顧客情報
	 */
	public void insert(Customer customer) {
		String sql = "INSERT INTO users (name,email,password,zipcode,address,telephone) values (:name,:email,:password,:zipcode,:address,:telephone)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(customer);
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスで顧客を検索します.
	 * 
	 * @param email メールアドレス
	 * @return 顧客情報
	 */
	public Customer findByEmail(String email) {
		String sql = "select id,name,email,password,zipcode,address,telephone from users where email=:email";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<Customer> customerList = template.query(sql, param, CUSTOMER_ROW_MAPPER);
		if (customerList.size() == 0) {
			return null;
		}else {
			return customerList.get(0);
		}
		
	}


}
