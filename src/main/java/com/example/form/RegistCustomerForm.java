package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * 顧客登録用のフォーム.
 * 
 * @author nao.yamada
 *
 */
public class RegistCustomerForm {

	/** 姓 */
	@NotBlank(message = "姓を入力してください")
	private String familyName;
	/** 名前 */
	@NotBlank(message = "名前を入力してください")
	private String firstName;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message="メールアドレスの形式が不正です")
	private String email;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	@Length(min=8, max=16, message="パスワードは８文字以上１６文字以内で設定してください")
	private String password;
	/** 確認用パスワード */
	@NotBlank(message = "確認用パスワードを入力してください")
	private String confirmationPassword;
	/** 郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;
	/** 住所 */
	@NotBlank(message = "住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{4}$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String telephone;

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "RegistCustomerForm [familyName=" + familyName + ", firstName=" + firstName + ", email=" + email
				+ ", password=" + password + ", confirmationPassword=" + confirmationPassword + ", zipcode=" + zipcode
				+ ", address=" + address + ", telephone=" + telephone + "]";
	}

}
