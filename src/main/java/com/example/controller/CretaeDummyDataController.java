package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.CreateDummyDataService;

@Repository
@RequestMapping("")
public class CretaeDummyDataController {

	/** 生成するオーダーアイテム数の上限値 */
	private final int MAX_ORDER_ITEM_NUM = 3;
	
	/** 数量の上限値 */
	private final int MAX_QUANTITY = 10;
	
	@Autowired
	private CreateDummyDataService service;
	
	/**
	 * ダミーデータ生成フォーム画面を表示.
	 * 
	 * @return ダミーデータ生成フォーム画面
	 */
	@RequestMapping("/toDummyForm")
	public String toFormPage() {
		return "create-dummy-data";
	}
	
	/**
	 * ダミーデータを生成する.
	 * 
	 * @param num 生成するダミーユーザー数
	 * @return　ダミーデータ生成フォーム画面
	 */
	@RequestMapping("/createDummyData")
	public String createDummyData(Integer num) {
		if(num != null) {
			service.createDummyData(num, MAX_ORDER_ITEM_NUM, MAX_QUANTITY);
		}
		
		return "redirect:/toDummyForm";
	}
}
