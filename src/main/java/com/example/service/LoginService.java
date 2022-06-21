package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.Customer;
import com.example.domain.LoginCustomer;
import com.example.repository.CustomerRepository;

/**
 * 認証情報を付加した顧客を操作するサービス
 * 
 * @author nao.yamada
 *
 */
@Service
public class LoginService implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * 顧客情報に認証情報を追加します.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new UsernameNotFoundException("そのEmailは登録されていません。");
		}
		// 権限付与の例
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
//		if(administrator.isAdmin()) {
//			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
//		}
		return new LoginCustomer(customer, authorityList);
	}
}
