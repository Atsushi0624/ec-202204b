package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.form.RegistCustomerForm;
import com.example.service.RegistCustomerService;

@Controller
@RequestMapping("/registCustomer")
public class RegistCustomerController {

	@Autowired
	private RegistCustomerService registCustomerService;

	@ModelAttribute
	public RegistCustomerForm setUpRegistCustomerForm() {
		return new RegistCustomerForm();
	}

	@RequestMapping("/toRegistration")
	public String toRegistration() {
		return "register_customer";
	}

	@RequestMapping("/regist")
	public String regist(@Validated RegistCustomerForm form, BindingResult result) {
		if (! form.getConfirmationPassword().equals(form.getPassword())) {
			FieldError fieldError = new FieldError(result.getObjectName(), "confirmationPassword", "パスワードと確認用パスワードが不一致です");
			result.addError(fieldError);
		}
		boolean isDuplicateEmail = registCustomerService.isDuplicateEmail(form.getEmail());
		if (isDuplicateEmail) {
			FieldError fieldError = new FieldError(result.getObjectName(), "email", "そのメールアドレスはすでに使われています");
			result.addError(fieldError);
		}
		if (result.hasErrors()) {
			return toRegistration();
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(form, customer);
		customer.setName(form.getFamilyName() + form.getFirstName());
		registCustomerService.resistCustomer(customer);
		return "redirect:/registCustomer/toRegistration";
	}

}
