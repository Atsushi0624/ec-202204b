package com.example.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.AdminAnalysisService;

@RequestMapping("")
@Controller
public class AdminAnalysisController {
	@Autowired
	private AdminAnalysisService adminAnalysisService;

	/**
	 * 商品別円グラフを表示.
	 * 
	 * @return 商品別円グラフ画面
	 */
	@RequestMapping("/pieChart")
	public String displayPieChart() {
		return "pie_chart";
	}

	@RequestMapping("/ranking")
	public String displayRanking(Model model, String age, String gender) {
		List<Map<Item, Integer>> itemList = adminAnalysisService.rankedItemList(age, gender);
		List<String> items = new ArrayList<>();
		List<Integer> sales = new ArrayList<>();
		for (Map<Item, Integer> itemMap : itemList) {
			Set<Item> keySet = itemMap.keySet();
			Iterator iterator = keySet.iterator();
			Item item = (Item) iterator.next();
			Integer sale = itemMap.get(item);
			items.add(item.getName());
			sales.add(sale);
		}
		model.addAttribute("items", items);
		model.addAttribute("sales", sales);

		// model.addAttribute("itemList", itemList);
		return "ranking";
	}
}
