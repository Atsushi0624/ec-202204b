package com.example.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.form.RegistCustomerForm;
import com.example.service.RegistCustomerService;

/**
 * 顧客情報を操作するコントローラ.
 * 
 * @author nao.yamada
 *
 */
@Controller
@RequestMapping("/registCustomer")
public class RegistCustomerController {

	/**
	 * 顧客登録用サービス.
	 * 
	 */
	@Autowired
	private RegistCustomerService registCustomerService;

	/**
	 * 顧客登録用フォームを用意します.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public RegistCustomerForm setUpRegistCustomerForm() {
		return new RegistCustomerForm();
	}

	/**
	 * 登録ページを表示します.
	 * 
	 * @return 登録ページ
	 */
	@RequestMapping("/toRegistration")
	public String toRegistration(Model model) {
		// 性別セレクトボックス
		Map<String, String> genderMap = new LinkedHashMap<>();
		genderMap.put("男性", "男性");
		genderMap.put("女性", "女性");
		model.addAttribute("genderMap", genderMap);

		// 年代セレクトボックス
		Map<String, String> ageMap = new LinkedHashMap<>();
		ageMap.put("10代", "10代");
		ageMap.put("20代", "20代");
		ageMap.put("30代", "30代");
		ageMap.put("40代", "40代");
		ageMap.put("50代", "50代");
		ageMap.put("60代", "60代");
		model.addAttribute("ageMap", ageMap);
		return "register_customer";
	}

	/**
	 * 顧客情報を登録します.
	 * 
	 * @param form   登録フォーム
	 * @param result エラーチェック用BindingResult
	 * @return ログインページ
	 */
	@RequestMapping("/regist")
	public String regist(@Validated RegistCustomerForm form, BindingResult result, Model model) {
		if (!form.getConfirmationPassword().equals(form.getPassword())) {
			FieldError fieldError = new FieldError(result.getObjectName(), "confirmationPassword", "パスワードと確認用パスワードが不一致です");
			result.addError(fieldError);
		}
		boolean isDuplicateEmail = registCustomerService.isDuplicateEmail(form.getEmail());
		if (isDuplicateEmail) {
			FieldError fieldError = new FieldError(result.getObjectName(), "email", "そのメールアドレスはすでに使われています");
			result.addError(fieldError);
		}
		if (result.hasErrors()) {
			return toRegistration(model);
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customer.setName(form.getFamilyName() + form.getFirstName());
		registCustomerService.resistCustomer(customer);
		return "redirect:/login/toLogin";
	}

}
