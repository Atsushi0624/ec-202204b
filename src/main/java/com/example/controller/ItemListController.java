package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Customer;
import com.example.domain.Item;
import com.example.service.AnalizeService;
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
	
	@Autowired
	private AnalizeService analizeService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 商品一覧を検索して表示する.
	 * 
	 * @param model    モデル
	 * @param itemName 検索する商品名
	 * @param sortKey  並び順を制御するリクエストパラメータ
	 * @return 商品一覧
	 */
	@RequestMapping("")
	public String showList(Model model, String itemName, String sortKey) {
		System.out.println();
		System.out.println("------------------------------------------------------------------------------");
		
		// ログイン状態のときおすすめ商品を表示させる
		Customer customer = (Customer)session.getAttribute("customer");
		if(customer != null) {
			List<Item> recommendItemList = analizeService.getRecommendItems();
			// 評価を0.0の形式に変更
			for(Item item: recommendItemList) {
				double averageRate = Math.round(item.getAverageRate() * 10) / 10.0;
				item.setAverageRate(averageRate);
			}
			model.addAttribute("recommendItemList", recommendItemList);
		}
		
		List<Item> itemList = null;
		// オートコンプリート用に全件リストを用意
		List<Item> allItemList = itemListService.findAll();
		model.addAttribute("allItemList", allItemList);
		if (itemName != null) {
			itemList = itemListService.searchByName(itemName);
			if (itemList.size() == 0) {
				model.addAttribute("noItemMessage", "一件も見つかりませんでした");
				itemList = itemListService.findAll();
			}
		} else {
			// 最初のページ表示時や不正なリクエストパラメータの時は価格順で表示させる
			if (sortKey == null) {
				itemList = itemListService.findAll();
				model.addAttribute("sortKey", "price");
			} else {
				if (sortKey.equals("rate")) {
					itemList = itemListService.findAllSortedByRate();
					model.addAttribute("sortKey", "rate");
				} else if (sortKey.equals("sales")) {
					itemList = itemListService.findAllSortedBySales();
					model.addAttribute("sortKey", "sales");
				} else {
					itemList = itemListService.findAll();
					model.addAttribute("sortKey", "price");
				}
			}
		}
		
		// 評価を0.0の形式に変更
		for(Item item: itemList) {
			double averageRate = Math.round(item.getAverageRate() * 10) / 10.0;
			item.setAverageRate(averageRate);
		}
		model.addAttribute("itemList", itemList);
		return "item_list";
	}
}
