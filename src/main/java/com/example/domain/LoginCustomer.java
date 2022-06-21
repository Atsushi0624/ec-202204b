package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 認証用のCustomerドメイン.
 * 
 * @author nao.yamada
 *
 */
public class LoginCustomer extends User {

	private static final long serialVersionUID = 1L;
	private final Customer customer;

	/**
	 * 通常の顧客情報に加え、認可用ロールを設定する。
	 * 
	 * @param customer      顧客情報
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginCustomer(Customer customer, Collection<GrantedAuthority> authorityList) {
		super(customer.getEmail(), customer.getPassword(), authorityList);
		this.customer = customer;
	}

	/**
	 * 顧客情報を返します.
	 * 
	 * @return 顧客情報
	 */
	public Customer getCustomer() {
		return customer;
	}

}
