package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.Customer;
import com.example.repository.CustomerRepository;

/**
 * 顧客情報を操作するサービス.
 * 
 * @author nao.yamada
 *
 */
@Service
public class RegistCustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	/**
	 * 顧客の登録をします.
	 * 
	 * @param customer 顧客情報
	 */
	public void resistCustomer(Customer customer) {
		// パスワードのハッシュ化
		String hashedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(hashedPassword);

		customerRepository.insert(customer);
	}
	
	/**
	 * 重複したメールアドレスが登録されようとしていないか確認します.
	 * 
	 * @param email メールアドレス
	 * @return 真偽値
	 */
	public boolean isDuplicateEmail(String email) {
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			return false;
		}else {
			return true;
		}
	}

}
