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
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Customer;
import com.example.domain.LoginCustomer;
import com.example.repository.CustomerRepository;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;

/**
 * 認証情報を付加した顧客を操作するサービス
 * 
 * @author nao.yamada
 *
 */
@Service
@Transactional
public class LoginService implements UserDetailsService {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
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
	
	/**
	 * ダミー顧客IDを正規の顧客IDに更新する処理.
	 * 未注文のオーダーが既に存在するかどうかで処理が変わる
	 * 
	 * @param dummyCustomerId ダミー顧客ID
	 * @param customerId 正規の顧客ID
	 */
	public void updateCustomerId(Integer dummyCustomerId, Integer customerId) {
		Integer orderId = orderRepository.findOrderIdByStatusAndUserId(0, customerId);
		if(orderId == null) {
			// 未注文のオーダーが存在しない場合は、そのまま更新
			orderRepository.updateCustomerId(dummyCustomerId, customerId);
		}else {
			// 未注文のオーダーが存在する場合は、そのオーダーに追加
			Integer newOrderId = orderRepository.findOrderIdByStatusAndUserId(0, dummyCustomerId);
			orderRepository.deleteById(newOrderId);
			orderItemRepository.updateCustomerId(orderId, newOrderId);
		}
	}
}
