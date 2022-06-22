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
	 * @param sortKey 並び順を制御するリクエストパラメータ
	 * @return 商品一覧
	 */
	@RequestMapping("/show")
	public String showList(Model model, String itemName, String sortKey) {
		List<Item> itemList = null;
		if (itemName != null) {
			itemList = itemListService.searchByName(itemName);
			if (itemList.size() == 0) {
				model.addAttribute("noItemMessage", "一件も見つかりませんでした");
				itemList = itemListService.findAll();
			}
		} else {
			// 最初のページ表示時や不正なリクエストパラメータの時はデフォルトの並び順で表示させる
			if (sortKey == null) {
				itemList = itemListService.findAll();
			} else {
				if (sortKey.equals("rate")) {
					itemList = itemListService.findAllSortedByRate();
				} else if (sortKey.equals("sales")) {
					itemList = itemListService.findAllSortedBySales();
				} else {
					itemList = itemListService.findAll();
				}
			}
		}
		model.addAttribute("itemList", itemList);
		return "item_list";
	}
}
