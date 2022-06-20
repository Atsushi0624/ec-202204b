package com.example.domain;

import lombok.Data;

/**
 * Userを表すドメイン.
 * 
 * @author nao.yamada
 *
 */
@Data
public class User {
	/**	ID */
	private Integer id;
	/**	名前 */
	private String name;
	/**	メールアドレス */
	private String email;
	/**	パスワード */
	private String password;
	/**	郵便番号 */
	private String zipcode;
	/**	住所 */
	private String address;
	/**	電話番号 */
	private String telephone;
	

}
