package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemListService;

/**
 * 商品情報を操作するコントローラー.
 * 
 * @author ryosuke.moritani
 *
 */
@Controller
@RequestMapping("")
public class ItemListController {
	@Autowired
	private ItemListService itemListService;

	/**
	 * 商品一覧を検索して表示する.
	 * 
	 * @param model    モデル
	 * @param itemName 検索する商品名
	 * @return 商品一覧
	 */
	@RequestMapping("/show")
	public String showList(Model model, String itemName) {
		List<Item> itemList = null;
		if (itemName != null) {
			itemList = itemListService.searchByName(itemName);
			if (itemList.size() == 0) {
				model.addAttribute("noItemMessage", "一件も見つかりませんでした");
				itemList = itemListService.findAll();
			}
		} else {
			itemList = itemListService.findAll();
		}
		model.addAttribute("itemList", itemList);
		return "item_list";
	}
}
