package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Customer;

/**
 * Customer情報を操作するリポジトリ.
 * 
 * @author nao.yamada
 *
 */
@Repository
public class CustomerReposiroty {
	
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
		
	}
	
	public Customer findByEmail(String email) {
		return null;
	}
	

}
